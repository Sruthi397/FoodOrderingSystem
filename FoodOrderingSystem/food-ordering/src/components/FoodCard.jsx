
import React from 'react';

function FoodCard({ food, onAddToCart }) {
  return (
    <div className="food-card">

      <img
        src={food.imageUrl}
        alt={food.name}
      />

      <h3>{food.name}</h3>

      <p>{food.description}</p>

      <h4>₹{food.price}</h4>

      <button onClick={() => onAddToCart(food)}>
        Add to Cart
      </button>

    </div>
  );
}

export default FoodCard;
