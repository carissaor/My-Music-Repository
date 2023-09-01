import React, {useState} from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Container, Paper, Button } from '@mui/material';

export default function Music () {
  const paperStyle ={padding: '50px 20px', width:600, margin: '200px auto'}
  const [title, setTitle] = useState('')
  const [artist, setArtist] = useState('')
  const handleClick= (e) => {
    e.preventDefault()
    const music = {title, artist}
    console.log(music)
    fetch("http://localhost:8080/music/add",{
      method: 'POST',
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(music)
    }).then(() => {
      console.log("New song added")
    })
  }
  return (
    <Container>
      <Paper elevation={3} style={paperStyle}>
        <h1>Add songs</h1>
        <Box
        component="form"
        sx={{
          '& > :not(style)': { m: 1, width: '25ch' },
        }}
        noValidate
        autoComplete="off"
        >
          <TextField id="outlined-basic" label="song title" variant="outlined" fullWidth 
          value = {title}
          onChange = {(e)=>setTitle(e.target.value)}
          />
          <TextField id="outlined-basic" label="artist" variant="outlined" fullWidth
          value = {artist}
          onChange = {(e)=>setArtist(e.target.value)}
          />
          <Button variant="contained" onClick={handleClick}
          >Add
          </Button>
        </Box>
      </Paper> 
    </Container>
    
  );
}
