
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function AddFood() {
  const navigate = useNavigate();

  const [food, setFood] = useState({
    name: '',
    description: '',
    price: '',
    category: '',
    imageUrl: '',
    available: true
  });

  const handleChange = (e) => {
    const { name, value } = e.target;

    setFood({
      ...food,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post('http://localhost:8080/api/foods', {
        ...food,
        price: Number(food.price),
        available: true
      });

      alert('Food added successfully!');
      navigate('/menu');

    } catch (error) {
      alert('Unable to add food!');
    }
  };

  return (
    <div className="register-container">
      <h2>Add New Food</h2>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="name"
          placeholder="Food Name"
          value={food.name}
          onChange={handleChange}
          required
        />

        <input
          type="text"
          name="description"
          placeholder="Food Description"
          value={food.description}
          onChange={handleChange}
          required
        />

        <input
          type="number"
          name="price"
          placeholder="Food Price"
          value={food.price}
          onChange={handleChange}
          min="1"
          required
        />

        <input
          type="text"
          name="category"
          placeholder="Food Category"
          value={food.category}
          onChange={handleChange}
          required
        />

        <input
          type="url"
          name="imageUrl"
          placeholder="Paste Food Image URL"
          value={food.imageUrl}
          onChange={handleChange}
          required
        />

        <button type="submit">Add Food</button>
      </form>
    </div>
  );
}

export default AddFood;
