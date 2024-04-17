import React, { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { Container, Typography, List, ListItem, ListItemText, Button } from '@mui/material';
import { useTranslation } from 'react-i18next';

const Profile = () => {
    const [cookies] = useCookies(['USER_ID']);
    const [user, setUser] = useState(null);
    const [articles, setArticles] = useState([]);
    const navigate = useNavigate();
    const { t, i18n } = useTranslation();

    useEffect(() => {
        if (!cookies.USER_ID) {
            navigate('/auth');
        } else {
            axios.get(`http://localhost/api/users/${cookies.USER_ID}`)
                .then(response => {
                    setUser(response.data);
                })
                .catch(error => {
                    console.error('Error fetching user profile:', error);
                });

            axios.get(`http://localhost/api/articles?userId=${cookies.USER_ID}`)
                .then(response => {
                    setArticles(response.data);
                })
                .catch(error => {
                    console.error('Error fetching user articles:', error);
                });
        }
    }, [cookies.USER_ID, navigate]);

    const handleDelete = (id) => {
        axios.delete(`http://localhost/api/articles/${id}`)
            .then(response => {
                // Remove the deleted article from the list
                setArticles(articles.filter(article => article.id !== id));
            })
            .catch(error => {
                console.error('Error deleting article:', error);
            });
    };

    if (!user || !articles) {
        return <Typography>Loading...</Typography>;
    }

    return (
        <Container>
            <Typography variant="h2">Profile</Typography>
            <Typography variant="h4">User: {user.username}</Typography>
            <Typography variant="h5">Articles:</Typography>
            <List>
                {articles.map(article => (
                    <ListItem key={article.id}>
                        <ListItemText
                            primary={i18n.language === 'en' ? article.title_en : article.title_ua}
                            secondary={i18n.language === 'en' ? article.description_en : article.description_ua}
                        />
                        <Button onClick={() => handleDelete(article.id)}>Delete</Button>
                        <Button component={Link} to={`/articles/${article.id}/edit`}>Edit</Button>
                    </ListItem>
                ))}
            </List>
        </Container>
    );
};

export default Profile;
