
import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Cart() {
  const userId = localStorage.getItem('userId');

  const [cartItems, setCartItems] = useState([]);
  const [foods, setFoods] = useState([]);

  const loadCart = async () => {
    try {
      const cartResponse = await axios.get(
        `http://localhost:8080/api/cart/${userId}`
      );

      const foodResponse = await axios.get(
        'http://localhost:8080/api/foods'
      );

      setCartItems(cartResponse.data);
      setFoods(foodResponse.data);
    } catch (error) {
      alert('Unable to load cart!');
    }
  };

  useEffect(() => {
    if (userId) {
      loadCart();
    }
  }, [userId]);

  const getFood = (foodId) => {
    return foods.find((food) => food.id === foodId);
  };

  const updateQuantity = async (id, quantity) => {
    if (quantity < 1) return;

    try {
      await axios.put(
        `http://localhost:8080/api/cart/update/${id}/${quantity}`
      );

      loadCart();
    } catch (error) {
      alert('Unable to update quantity!');
    }
  };

  const removeItem = async (id) => {
    try {
      await axios.delete(
        `http://localhost:8080/api/cart/remove/${id}`
      );

      loadCart();
    } catch (error) {
      alert('Unable to remove item!');
    }
  };

  // Place order for individual item
  const placeSingleOrder = async (cartItemId) => {
    try {
      const response = await axios.post(
        `http://localhost:8080/api/orders/place-single/${userId}/${cartItemId}`
      );

      alert('Order placed successfully! Order ID: ' + response.data.id);

      loadCart();
    } catch (error) {
      alert(error.response?.data?.message || 'Unable to place order!');
    }
  };

  // Place order for all cart items
  const placeOrder = async () => {
    try {
      const response = await axios.post(
        `http://localhost:8080/api/orders/place/${userId}`
      );

      alert('Order placed successfully! Order ID: ' + response.data.id);

      loadCart();
    } catch (error) {
      alert(error.response?.data?.message || 'Unable to place order!');
    }
  };

  const total = cartItems.reduce((sum, item) => {
    const food = getFood(item.foodId);
    return sum + (food ? food.price * item.quantity : 0);
  }, 0);

  if (!userId) {
    return <h2>Please login to view your cart.</h2>;
  }

  return (
    <div className="cart-container">
      <h2>Your Cart</h2>

      {cartItems.length === 0 ? (
        <p>Your cart is empty!</p>
      ) : (
        <>
          {cartItems.map((item) => {
            const food = getFood(item.foodId);

            if (!food) return null;

            return (
              <div className="cart-item" key={item.id}>

                <img
                  src={food.imageUrl}
                  alt={food.name}
                  style={{
                    width: '150px',
                    height: '150px',
                    objectFit: 'cover',
                    borderRadius: '5px'
                  }}
                />

                <div>
                  <h3>{food.name}</h3>

                  <p>Price: ₹{food.price}</p>

                  <p>Quantity: {item.quantity}</p>

                  <button
                    onClick={() =>
                      updateQuantity(item.id, item.quantity - 1)
                    }
                  >
                    -
                  </button>

                  <button
                    onClick={() =>
                      updateQuantity(item.id, item.quantity + 1)
                    }
                  >
                    +
                  </button>

                  <button onClick={() => removeItem(item.id)}>
                    Remove
                  </button>

                  <button onClick={() => placeSingleOrder(item.id)}>
                    Place Order
                  </button>
                </div>
              </div>
            );
          })}

          <h3>Total Amount: ₹{total.toFixed(2)}</h3>

          <button onClick={placeOrder}>
            Place Order for All Items
          </button>
        </>
      )}
    </div>
  );
}

export default Cart;
