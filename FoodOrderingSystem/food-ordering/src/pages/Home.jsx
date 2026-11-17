
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function Home() {
  const [userName, setUserName] = useState(
    localStorage.getItem('userName')
  );

  useEffect(() => {
    const handleUserChange = () => {
      setUserName(localStorage.getItem('userName'));
    };

    window.addEventListener('userLogin', handleUserChange);
    window.addEventListener('userLogout', handleUserChange);

    return () => {
      window.removeEventListener('userLogin', handleUserChange);
      window.removeEventListener('userLogout', handleUserChange);
    };
  }, []);

  return (
    
    <div className="home"style={{
    backgroundImage: "url('https://static.vecteezy.com/system/resources/thumbnails/066/058/462/small/a-table-with-various-types-of-food-and-drinks-photo.jpeg')",
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    backgroundRepeat: 'no-repeat',
    minHeight: '90vh'
  }}>
      <h1>Welcome to Foodie!</h1>
      <h2>Delicious Food at Affordable Prices</h2>
      <p>
        Order your favourite food online.
        Find tasty meals that fit your budget!
      </p>

      <Link to="/menu">
        <button>Explore Menu</button>
      </Link>

      <Link to="/budget">
        <button>Find Meals Within My Budget</button>
      </Link>
    </div>
  );
}

export default Home;
