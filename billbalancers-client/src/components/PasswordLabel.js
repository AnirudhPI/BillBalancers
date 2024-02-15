import React, {useState } from 'react';
import {TextField, IconButton } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';

const EditableLabel = ({profile,onUpdate}) =>{

    const [editMode, setEditMode] = useState(false);
  
    const [currentPassword, setCurrentPassword] = useState('');

    const [updatedPassword,setUpdatedPassword] = useState('');

    const hiddenPassword = "*".repeat(10)

    const handleCurrentPasswordChange = (event) => {
        setCurrentPassword(event.target.value);
        onUpdate('password',event.target.value); 
    };
    const handleUpdatedPasswordChange = (event) =>{
      setUpdatedPassword(event.target.value)
      onUpdate('updatedPassword',event.target.value)
    }
    const toggleEditMode = () => {
        setEditMode(!editMode);
    };

    const onClick = () => {
        console.log('hello');
    };

    return(
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px',marginBottom: '16px' }}>
                {editMode ? (
                  <>
                  <div style={{ display: 'flex', flexDirection: 'column'}}>
                    <TextField
                    value={currentPassword}
                    label="Current Password"
                    type="password"
                    onChange={handleCurrentPasswordChange}
                    style={{marginBottom: "10px"}}
                    />
                     <TextField
                    value={updatedPassword}
                    label="New Password"
                    type="password"
                    onChange={handleUpdatedPasswordChange}
                    />
                    </div>
                   </>
                ) : (
                    <>
                        <span>{hiddenPassword}</span>
                    <IconButton onClick={toggleEditMode} aria-label="edit">
                        <EditIcon />
                    </IconButton>
                </>
                )}
                
            </div>
    )
}

export default EditableLabel;

