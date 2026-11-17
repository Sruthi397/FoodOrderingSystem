
import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Orders() {
  const userId = localStorage.getItem('userId');

  const [orders, setOrders] = useState([]);
  const [foods, setFoods] = useState([]);

  const loadOrders = async () => {
    try {
      const orderResponse = await axios.get(
        `http://localhost:8080/api/orders/history/${userId}`
      );

      const foodResponse = await axios.get(
        'http://localhost:8080/api/foods'
      );

      setOrders(orderResponse.data);
      setFoods(foodResponse.data);
    } catch (error) {
      alert('Unable to load orders!');
    }
  };

  useEffect(() => {
    if (userId) {
      loadOrders();
    }
  }, [userId]);

  const getFood = (foodId) => {
    return foods.find((food) => food.id === foodId);
  };

  // Cancel individual order
  const cancelOrder = async (orderId) => {
    const confirmCancel = window.confirm(
      'Are you sure you want to cancel this order?'
    );

    if (!confirmCancel) return;

    try {
      await axios.put(
        `http://localhost:8080/api/orders/status/${orderId}?status=CANCELLED`
      );

      alert('Order cancelled successfully!');

      loadOrders();
    } catch (error) {
      alert(
        error.response?.data?.message || 'Unable to cancel order!'
      );
    }
  };

  if (!userId) {
    return <h2>Please login to view your orders.</h2>;
  }

  return (
    <div className="orders-container">
      <h2>My Orders</h2>

      {orders.length === 0 ? (
        <p>No orders found!</p>
      ) : (
        orders.map((order) => (
          <OrderCard
            key={order.id}
            order={order}
            getFood={getFood}
            cancelOrder={cancelOrder}
          />
        ))
      )}
    </div>
  );
}

function OrderCard({ order, getFood, cancelOrder }) {
  const [items, setItems] = useState([]);

  useEffect(() => {
    const loadItems = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/orders/${order.id}/items`
        );

        setItems(response.data);
      } catch (error) {
        console.error('Unable to load order items');
      }
    };

    loadItems();
  }, [order.id]);

  return (
    <div className="order-card">

      <h3>Order ID: {order.id}</h3>

      <p>Total Amount: ₹{order.totalAmount}</p>

      <p>Status: {order.status}</p>

      <p>Order Date: {order.orderDate}</p>

      <p>Expected Delivery: {order.expectedDeliveryTime}</p>

      <h4>Ordered Food:</h4>

      {items.map((item) => {
        const food = getFood(item.foodId);

        return (
          <div
            className="order-food-item"
            key={item.id}
            style={{
              display: 'flex',
              alignItems: 'center',
              gap: '10px',
              margin: '10px 0'
            }}
          >
            {food && (
              <img
                src={food.imageUrl}
                alt={food.name}
                style={{
                  width: '100px',
                  height: '100px',
                  objectFit: 'cover',
                  borderRadius: '5px'
                }}
              />
            )}

            <span>
              {food ? food.name : `Food ID: ${item.foodId}`}
              {' - '}
              Quantity: {item.quantity}
            </span>
          </div>
        );
      })}

      {order.status === 'PLACED' && (
        <button onClick={() => cancelOrder(order.id)}>
          Cancel Order
        </button>
      )}

    </div>
  );
}

export default Orders;
