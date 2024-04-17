import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import axios from 'axios'; // Assuming axios is imported from 'axios' package
import {useCookies} from 'react-cookie'; // Assuming useCookies is imported from 'react-cookie' package
import {Container, Typography, List, ListItem, ListItemText} from '@mui/material'; // Assuming MUI components are used
import {useTranslation} from 'react-i18next';

const Profile = () => {
    const [cookies] = useCookies(['USER_ID']);
    const [user, setUser] = useState(null);
    const [articles, setArticles] = useState([]);
    const navigate = useNavigate();
    const {t, i18n} = useTranslation();

    useEffect(() => {
        // Check if user is logged in
        if (!cookies.USER_ID) {
            navigate('/auth'); // Redirect to login page if not logged in
        } else {
            // Fetch user's profile
            axios.get(`http://localhost/api/users/${cookies.USER_ID}`)
                .then(response => {
                    setUser(response.data);
                })
                .catch(error => {
                    console.error('Error fetching user profile:', error);
                });

            // Fetch user's articles
            axios.get(`http://localhost/api/articles?userId=${cookies.USER_ID}`)
                .then(response => {
                    setArticles(response.data);
                })
                .catch(error => {
                    console.error('Error fetching user articles:', error);
                });
        }
    }, [cookies.USER_ID, navigate]);

    if (!user || !articles) {
        return <Typography>Loading...</Typography>; // Show loading indicator while fetching data
    }

    return (
        <Container>
            <Typography variant="h2">Profile</Typography>
            <Typography variant="h4">User: {user.username}</Typography>
            <Typography variant="h5">Articles:</Typography>
            <List>
                {articles.map(article => (
                    <ListItem key={article.id}>
                        <ListItemText primary={i18n.language === 'en' ? article.title_en : article.title_ua}
                                      secondary={i18n.language === 'en' ? article.description_en : article.description_ua}/>
                    </ListItem>
                ))}
            </List>
        </Container>
    );
};

export default Profile;
