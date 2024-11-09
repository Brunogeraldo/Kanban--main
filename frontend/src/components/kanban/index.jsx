// src/components/kanban/index.jsx
import './kanban.scss';
import { DragDropContext, Draggable, Droppable } from 'react-beautiful-dnd';
import mockData from '../../mockData';
import { useState } from 'react';
import Card from '../card';

const Kanban = () => {
    const [data, setData] = useState(mockData);
    const [newTaskTitle, setNewTaskTitle] = useState('');
    const [newTaskDescription, setNewTaskDescription] = useState('');
    const [selectedColumn, setSelectedColumn] = useState(data[0].id);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editingTask, setEditingTask] = useState(null);
    const [editingTaskTitle, setEditingTaskTitle] = useState('');
    const [editingTaskDescription, setEditingTaskDescription] = useState('');

    const onDragEnd = (result) => {
        if (!result.destination) return;
        const { source, destination } = result;

        if (source.droppableId !== destination.droppableId) {
            const sourceColIndex = data.findIndex(e => e.id === source.droppableId);
            const destinationColIndex = data.findIndex(e => e.id === destination.droppableId);

            const sourceCol = data[sourceColIndex];
            const destinationCol = data[destinationColIndex];

            const sourceTask = [...sourceCol.tasks];
            const destinationTask = [...destinationCol.tasks];

            const [removed] = sourceTask.splice(source.index, 1);
            destinationTask.splice(destination.index, 0, removed);

            data[sourceColIndex].tasks = sourceTask;
            data[destinationColIndex].tasks = destinationTask;

            setData([...data]);
        }
    };

    const openModal = (task) => {
        setEditingTask(task);
        setEditingTaskTitle(task.title);
        setEditingTaskDescription(task.description);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setEditingTask(null);
        setEditingTaskTitle('');
        setEditingTaskDescription('');
    };

    const updateTask = () => {
        if (editingTaskTitle.trim() === '' || editingTaskDescription.trim() === '') return;

        const columnIndex = data.findIndex(col => col.tasks.some(t => t.id === editingTask.id));
        const taskIndex = data[columnIndex].tasks.findIndex(t => t.id === editingTask.id);

        data[columnIndex].tasks[taskIndex].title = editingTaskTitle;
        data[columnIndex].tasks[taskIndex].description = editingTaskDescription;
        setData([...data]);
        closeModal();
    };

    const deleteTask = () => {
        const columnIndex = data.findIndex(col => col.tasks.some(t => t.id === editingTask.id));
        data[columnIndex].tasks = data[columnIndex].tasks.filter(t => t.id !== editingTask.id);
        setData([...data]);
        closeModal();
    };

    return (
        <DragDropContext onDragEnd={onDragEnd}>
            <div className="kanban">
                {data.map(section => (
                    <Droppable key={section.id} droppableId={section.id}>
                        {(provided) => (
                            <div {...provided.droppableProps} className='kanban__section' ref={provided.innerRef}>
                                <div className="kanban__section__title">
                                    {section.title}
                                    <button className="kanban__add-button" onClick={() => openModal(section.id)}>+</button>
                                </div>
                                <div className="kanban__section__content">
                                    {section.tasks.map((task, index) => (
                                        <Draggable key={task.id} draggableId={task.id} index={index}>
                                            {(provided) => (
                                                <div
                                                    ref={provided.innerRef}
                                                    {...provided.draggableProps}
                                                    {...provided.dragHandleProps}
                                                    onClick={() => openModal(task)} // Abre o modal ao clicar no cartão
                                                    style={{
                                                        ...provided.draggableProps.style,
                                                        cursor: 'pointer'
                                                    }}
                                                >
                                                    <Card title={task.title} description={task.description} />
                                                </div>
                                            )}
                                        </Draggable>
                                    ))}
                                </div>
                            </div>
                        )}
                    </Droppable>
                ))}
            </div>
            {isModalOpen && (
                <div className="modal">
                    <h2>Editar Tarefa</h2>
                    <input
                        type="text"
                        value={editingTaskTitle}
                        onChange={(e) => setEditingTaskTitle(e.target.value)}
                        placeholder="Título da tarefa"
                    />
                    <textarea
                        value={editingTaskDescription}
                        onChange={(e) => setEditingTaskDescription(e.target.value)}
                        placeholder="Descrição da tarefa"
                    />
                    <button onClick={updateTask}>Salvar</button>
                    <button onClick={deleteTask}>Excluir</button>
                    <button onClick={closeModal}>Cancelar</button>
                </div>
            )}
        </DragDropContext>
    );
};

export default Kanban;