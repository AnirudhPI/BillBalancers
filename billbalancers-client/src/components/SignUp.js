import React, { useState } from 'react';
import { Container, Typography, TextField, Button } from '@mui/material';
import axios from 'axios';

const SignupForm = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    // console.log('Form submitted with data:', formData);
    console.log(formData)
    const {data} = await axios.post('http://localhost:8080/auth/signup', formData);
    console.log('response: ', data);
    setFormData({
      firstName: '',
      lastName: '',
      email: '',
      password: '',
    });

  };

  return (
    <Container
      maxWidth="xs"
      style={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'flex-start',
        height: '80vh',
        marginTop: '5vh',
      }}
    >
      <Typography variant="h4" align="center" gutterBottom>
        Sign Up
      </Typography>
      <form
        onSubmit={handleSubmit}
        style={{
          width: '100%',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <TextField
          label="First Name"
          fullWidth
          margin="normal"
          name="firstName"
          value={formData.firstName}
          onChange={handleChange}
        />
        <TextField
          label="Last Name"
          fullWidth
          margin="normal"
          name="lastName"
          value={formData.lastName}
          onChange={handleChange}
        />
        <TextField
          label="Email"
          fullWidth
          margin="normal"
          name="email"
          type="email"
          value={formData.email}
          onChange={handleChange}
        />
        <TextField
          label="Password"
          fullWidth
          margin="normal"
          name="password"
          type="password"
          value={formData.password}
          onChange={handleChange}
        />
        <Button
          type="submit"
          variant="contained"
          color="primary"
          fullWidth
          style={{ marginTop: '20px' }}
        >
          Sign Up
        </Button>
      </form>
    </Container>
  );
};

export default SignupForm;
