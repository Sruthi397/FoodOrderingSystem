
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import FoodCard from '../components/FoodCard';

function Menu() {

  const [foods, setFoods] = useState([]);

  useEffect(() => {
    getFoods();
  }, []);

  const getFoods = async () => {
    try {
      const response = await axios.get(
        'http://localhost:8080/api/foods'
      );

      setFoods(response.data);

    } catch (error) {
      alert('Unable to load food items!');
    }
  };

  const addToCart = async (food) => {

    const userId = localStorage.getItem('userId');

    if (!userId) {
      alert('Please login first!');
      return;
    }

    try {
      await axios.post(
        'http://localhost:8080/api/cart/add',
        {
          userId: Number(userId),
          foodId: food.id,
          quantity: 1
        }
      );

      alert(food.name + ' added to cart!');

    } catch (error) {
      alert('Unable to add food to cart!');
    }
  };

  return (
    <div className="menu-container">

      <h1>Our Food Menu</h1>

      <div className="food-list">

        {foods.filter(food => food.available).map(food => (
          <FoodCard
            key={food.id}
            food={food}
            onAddToCart={addToCart}
          />
        ))}

      </div>

    </div>
  );
}

export default Menu;
