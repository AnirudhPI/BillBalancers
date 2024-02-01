import React, { useState } from 'react';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import LoginForm from './LoginForm';
import SignupForm from './SignUp';

export default function MainComponent() {
    const [activeForm, setActiveForm] = useState('login');

    return (
        <Box 
          sx={{
            display: 'flex', 
            flexDirection: 'column', 
            justifyContent: 'flex-start', 
            alignItems: 'center', 
            height: '100vh', 
            paddingTop: '10vh'
          }}
        >
            <Box sx={{ marginBottom: '20px' }}>
                <Button
                    variant={activeForm === 'login' ? 'contained' : 'outlined'}
                    onClick={() => setActiveForm('login')}
                    sx={{ margin: '0 10px' }}
                >
                    Login
                </Button>
                <Button
                    variant={activeForm === 'signup' ? 'contained' : 'outlined'}
                    onClick={() => setActiveForm('signup')}
                    sx={{ margin: '0 10px' }}
                >
                    Signup
                </Button>
            </Box>
            {activeForm === 'login' && <LoginForm />}
            {activeForm === 'signup' && <SignupForm />}
        </Box>
    );
}
