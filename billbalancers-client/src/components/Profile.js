import React, { useState } from 'react';
import { Container, Typography, TextField, Button } from '@mui/material';
import EditableLabel from './EditableLabel';

const Profile = () => {

    const [editMode, setEditMode] = useState(false);
  
    // State to keep track of the label's value
    const [labelValue, setLabelValue] = useState('Editable Label');

    // Function to handle the change of the text field
    const handleTextChange = (event) => {
        setLabelValue(event.target.value);
    };

    // Function to toggle the edit mode
    const toggleEditMode = () => {
        setEditMode(!editMode);
    };

    return (
        <Container style={{
            paddingLeft: 0, 
            paddingRight: 0, 
            marginLeft: 0, 
            marginRight: 0,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'flex-start'
        }}>
            <Typography variant="h4" align="left" gutterBottom>
                Profile
            </Typography>
            <EditableLabel/>
            <EditableLabel/>
            <EditableLabel/>
            <Button
                type="submit"
                variant="contained"
                color="primary"
                style={{ marginTop: '20px', width: '40%' }} 
            >
                Profile
            </Button>
        </Container>
    );
}

export default Profile;
