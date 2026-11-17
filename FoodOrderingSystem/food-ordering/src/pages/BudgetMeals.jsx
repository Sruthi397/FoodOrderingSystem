import React, { useState } from 'react';
import axios from 'axios';
import FoodCard from '../components/FoodCard';

function BudgetMeals() {
  const [budget, setBudget] = useState('');
  const [foods, setFoods] = useState([]);

  const searchFoods = async () => {
    if (!budget || Number(budget) <= 0) {
      alert('Please enter a valid budget!');
      return;
    }

    try {
      const response = await axios.get(
        `http://localhost:8080/api/foods/budget/${budget}`
      );
      setFoods(response.data);
    } catch (error) {
      alert('Unable to find food items!');
    }
  };

  const addToCart = async (food) => {
    const userId = localStorage.getItem('userId');

    if (!userId) {
      alert('Please login first!');
      return;
    }

    try {
      await axios.post('http://localhost:8080/api/cart/add', {
        userId: Number(userId),
        foodId: food.id,
        quantity: 1
      });

      alert(food.name + ' added to cart!');
    } catch (error) {
      alert('Unable to add food to cart!');
    }
  };

  return (
    <div className="budget-container">
      <h1>Find Meals Within Your Budget</h1>

      <input
        type="number"
        placeholder="Enter your budget in ₹"
        value={budget}
        onChange={(e) => setBudget(e.target.value)}
      />

      <button onClick={searchFoods}>Find Meals</button>

      <div className="food-list">
        {foods.map(food => (
          <FoodCard
            key={food.id}
            food={food}
            onAddToCart={addToCart}
          />
        ))}
      </div>

      {foods.length === 0 && (
        <p>Enter your budget to find affordable meals.</p>
      )}
    </div>
  );
}

export default BudgetMeals;