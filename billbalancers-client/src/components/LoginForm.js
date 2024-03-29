import React, { useState } from 'react';
import { Container, Typography, TextField, Button } from '@mui/material';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const LoginForm = () => {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log('Form submitted with data:', formData);
        const response = await axios.post('http://localhost:8080/auth/login', formData);
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('userID',response.data.userID);
        if (response.status === 200) {
			      navigate("/profile");
        }
        setFormData({
          email: '',
          password: '',
        });
    };

    const handleChange = (e) => {
      const { name, value } = e.target;
      setFormData({
        ...formData,
        [name]: value,
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
            Login
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
              Login
            </Button>
          </form>
        </Container>
    );

    
}

export default LoginForm;