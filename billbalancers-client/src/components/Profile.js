import React, {useEffect, useState} from 'react';
import { Container, Typography, Button } from '@mui/material';
import EditableLabel from './EditableLabel';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';
import OutlinedInput from '@mui/material/OutlinedInput';
import axios from 'axios';

const Profile = () => {

    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');

    const [profileData, setProfileData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: ''
    });
    const jwtToken = localStorage.getItem('token');

    const callProfile = async () => {
        try {
            const response = await axios.get('http://localhost:8080/auth/getDetails', {
                headers: {
                    'jwtToken': jwtToken
                }
            });
            console.log('response:', response);
            const {data} = response; 
            setProfileData({
                firstName:data.firstName,
                lastName:data.lastName,
                email:data.email,
                password:data.password,
            })
            console.log(profileData);
        }
        catch (error) {
            console.log(error)
            setSnackbarMessage('Failed to fetch details. Please try again.');
            setOpenSnackbar(true);
        }
        
    }

    const handleProfileChange = (field,value) =>{
        setProfileData(
            {
                ...profileData,
                [field] : value
            }
        )
    }

    const updateProfile = async () => {
        console.log(profileData)
        try {
            const response = await axios.put('http://localhost:8080/auth/signup', profileData);
        }
        catch (error) {
            setOpenSnackbar(true);
            setSnackbarMessage('Failed to fetch details. Please try again.');
        }
    }

    useEffect(() => {
        callProfile()
    },[]);

    return (
        <Container style={{
            paddingLeft: 10, 
            paddingRight: 0,
            paddingTop: 10, 
            marginLeft: 0, 
            marginRight: 0,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'flex-start'
        }}>
            <Typography variant="h4" align="left" gutterBottom>
                Profile
            </Typography>
            <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={() => setOpenSnackbar(false)} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
                <Alert onClose={() => setOpenSnackbar(false)} severity="error" sx={{ width: '100%' }}>
                    {snackbarMessage}
                </Alert>
            </Snackbar>
            <EditableLabel onUpdate = {(value) => handleProfileChange('firstName',value)} profile={profileData.firstName}/>
            <EditableLabel onUpdate = {(value) => handleProfileChange('lastName',value)} profile={profileData.lastName}/>
            <EditableLabel onUpdate = {(value) => handleProfileChange('email',value)} profile={profileData.email}/>
            <EditableLabel onUpdate = {(value) => handleProfileChange('password',value)} profile={profileData.password}/>
            <OutlinedInput
                id="outlined-adornment-password"
                type='password'
                label="Password"
            />
            <Button
                type="submit"
                variant="contained"
                color="primary"
                style={{ marginTop: '20px', width: '40%' }} 
                onClick={updateProfile}
            >
                Save
            </Button>
        </Container>
    );
}

export default Profile;
