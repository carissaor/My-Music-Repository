import React, { useState } from 'react';
import { Button } from '@mui/material';

function GetRecommendation() {
  const [recommendations, setRecommendations] = useState([]);

  const handleGetRecommendation = async () => {
    try {
      const response = await fetch("http://localhost:8080/music/getRecommendation"); // Change this URL to match your backend API endpoint
      const data = await response.json();
      setRecommendations(data);
      console.log(data)
    } catch (error) {
      console.error('Error getting recommendations:', error);
    }
  };

  return (
    <div>
      <Button variant="outlined" onClick={handleGetRecommendation}>
        Get Recommendation
      </Button>
      {/* Display recommended songs */}
      {recommendations.map((song, index) => (
        <div key={index}>
          <p>{song.name}</p>
          <p>{song.artist}</p>
        </div>
      ))}
    </div>
  );
}

export default GetRecommendation;
