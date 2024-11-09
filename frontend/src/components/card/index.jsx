// src/components/card/index.jsx
import React from 'react';
import './card.scss';

const Card = ({ title, description }) => {
    return (
        <div className="card">
            <h4 className="card__title">{title}</h4>
            <p className="card__description">{description}</p>
        </div>
    );
};

export default Card;