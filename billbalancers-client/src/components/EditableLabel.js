import React, { useState } from 'react';
import { Container, Typography, TextField, Button } from '@mui/material';

const EditableLabel = () =>{
    const [editMode, setEditMode] = useState(false);
  
    const [labelValue, setLabelValue] = useState('Editable Label');

    const handleTextChange = (event) => {
        setLabelValue(event.target.value);
    };

    const toggleEditMode = () => {
        setEditMode(!editMode);
    };

    return(
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px',marginBottom: '16px' }}>
                {editMode ? (
                    <TextField
                    value={labelValue}
                    onChange={handleTextChange}
                    onBlur={toggleEditMode}
                    autoFocus
                    />
                ) : (
                    <span>{labelValue}</span>
                )}
                <Button onClick={toggleEditMode} variant="outlined">
                    {editMode ? 'Save' : 'Edit'}
                </Button>
            </div>
    )
}

export default EditableLabel;

