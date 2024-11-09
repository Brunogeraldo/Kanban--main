// src/App.jsx
import './App.scss';
import Kanban from './components/kanban';
import Login from './components/Login';
import { useState } from 'react';

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const handleLogin = () => {
        setIsLoggedIn(true);
    };

    return (
        <div style={{ padding: '50px' }}>
            <h1 style={{ marginBottom: '20px' }}>
                Kanban UI
            </h1>
            {isLoggedIn ? <Kanban /> : <Login onLogin={handleLogin} />}
        </div>
    );
}

export default App;