
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Navbar from './components/Navbar';

import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Menu from './pages/Menu';
import BudgetMeals from './pages/BudgetMeals';
import Cart from './pages/Cart';
import Orders from './pages/Orders';
import AddFood from './pages/AddFood';

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/menu" element={<Menu />} />
        <Route path="/budget" element={<BudgetMeals />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/orders" element={<Orders />} />
        <Route path="/add-food" element={<AddFood />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
