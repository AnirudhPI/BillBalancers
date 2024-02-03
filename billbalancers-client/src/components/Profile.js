import React, {useEffect, useState} from 'react';
import { Container, Typography, Button } from '@mui/material';
import EditableLabel from './EditableLabel';
import axios from 'axios';

const Profile = () => {

    const [profileData, setProfileData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: 'test1'
    });
    const jwtToken = localStorage.getItem('token');

    const callProfile = async () => {

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
            password : 'test1'
            //password:data.password,
        })
        console.log(profileData);
    }

    const updateProfile = async () => {
        const response = await axios.put('http://localhost:8080/auth/signup', profileData);
        console.log(response);
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
            <EditableLabel profile={profileData.firstName}/>
            <EditableLabel profile={profileData.lastName}/>
            <EditableLabel profile={profileData.email}/>
            <EditableLabel profile={profileData.password}/>
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
