// NavBar.js
import React from 'react';
import { Link } from 'react-router-dom';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

export default function NavBar() {
    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    Billbalancers
                </Typography>
                <Button color="inherit" component={Link} to="/loginregister">Login/Signup</Button>
                {/* <Button color="inherit" component={Link} to="/signup">Signup</Button> */}
                <Button color="inherit" component={Link} to="/profile">Profile</Button>
            </Toolbar>
        </AppBar>
    );
}
