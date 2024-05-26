import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button, Select, MenuItem } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';

const AdminComponent = () => {
    const [cookies] = useCookies(['USER_ID']);
    const [users, setUsers] = useState([]);
    const [roleEdits, setRoleEdits] = useState({});
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (!cookies.USER_ID) {
            navigate('/auth');
        } else {
            axios.get(`http://localhost/api/users/${cookies.USER_ID}`)
                .then(response => {
                    if (!response.data.roles.includes('ROLE_ADMIN')) {
                        setError('This page is not allowed for you');
                    } else {
                        axios.get('http://localhost/api/users')
                            .then(response => {
                                setUsers(response.data);
                            })
                            .catch(error => {
                                console.error('Error fetching users:', error);
                            });
                    }
                })
                .catch(error => {
                    console.error('Error fetching user profile:', error);
                });
        }
    }, [cookies.USER_ID, navigate]);

    const handleDelete = (id) => {
        axios.delete(`http://localhost/api/users/${id}`)
            .then(response => {
                setUsers(users.filter(user => user.id !== id));
            })
            .catch(error => {
                console.error('Error deleting user:', error);
            });
    };

    const handleRoleChange = (id, role) => {
        setRoleEdits(prevState => ({ ...prevState, [id]: role }));
    };

    const handleRoleEdit = (id) => {
        const role = roleEdits[id];
        if (role) {
            axios.patch(`http://localhost/api/users/${id}`, { id, role: [role] })
                .then(response => {
                    setUsers(users.map(user => user.id === id ? { ...user, role: [role] } : user));
                })
                .catch(error => {
                    console.error('Error updating user role:', error);
                });
        }
    };

    if (error) {
        return <Typography variant="h4" color="error">{error}</Typography>;
    }

    return (
        <Container>
            <Typography variant="h2">Admin Panel</Typography>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Username</TableCell>
                            <TableCell>Role</TableCell>
                            <TableCell>Delete</TableCell>
                            <TableCell>Edit</TableCell>
                            <TableCell>More information</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {users.map(user => (
                            <TableRow key={user.id}>
                                <TableCell>{user.username}</TableCell>
                                <TableCell>{user.roles.join(', ')}</TableCell>
                                <TableCell>
                                    <Button
                                        variant="contained"
                                        color="secondary"
                                        onClick={() => handleDelete(user.id)}
                                    >
                                        Delete
                                    </Button>
                                </TableCell>
                                <TableCell>
                                    <Select
                                        value={roleEdits[user.id] || user.roles[0]}
                                        onChange={(e) => handleRoleChange(user.id, e.target.value)}
                                    >
                                        <MenuItem value="ROLE_ADMIN">Admin</MenuItem>
                                        <MenuItem value="ROLE_EDITOR">Editor</MenuItem>
                                    </Select>
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        onClick={() => handleRoleEdit(user.id)}
                                    >
                                        Edit
                                    </Button>
                                </TableCell>
                                <TableCell>
                                    <Button
                                        variant="contained"
                                        component={Link}
                                        to={`/users/${user.id}`}
                                    >
                                        View more
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
    );
};

export default AdminComponent;
