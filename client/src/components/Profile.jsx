import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import axios from 'axios';
import {useCookies} from 'react-cookie';
import {Button, Container, List, ListItem, ListItemText, Typography} from '@mui/material';
import {useTranslation} from 'react-i18next';

const Profile = () => {
    const [cookies] = useCookies(['USER_ID']);
    const [user, setUser] = useState(null);
    const [profileUser, setProfileUser] = useState(null);
    const [articles, setArticles] = useState([]);
    const navigate = useNavigate();
    const {t, i18n} = useTranslation();
    const {id} = useParams();

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

            axios.get(`http://localhost/api/users/${id}`)
                .then(response => {
                    setProfileUser(response.data);
                })
                .catch(error => {
                    console.error('Error fetching profile user:', error);
                });

            axios.get(`http://localhost/api/articles?userId=${id}`)
                .then(response => {
                    setArticles(response.data);
                })
                .catch(error => {
                    console.error('Error fetching user articles:', error);
                });
        }
    }, [cookies.USER_ID, id, navigate]);

    const handleDelete = (articleId) => {
        axios.delete(`http://localhost/api/articles/${articleId}`)
            .then(response => {
                setArticles(articles.filter(article => article.id !== articleId));
            })
            .catch(error => {
                console.error('Error deleting article:', error);
            });
    };

    if (!user || !profileUser || !articles) {
        return <Typography>Loading...</Typography>;
    }

    const isAdmin = user.roles.includes('ROLE_ADMIN');
    const isProfileOwner = user.id === parseInt(id);

    return (
        <Container>
            <Typography variant="h2">{t('profile.title')}</Typography>
            <Typography variant="h4">{t('profile.user')}: {profileUser.username}</Typography>
            {isAdmin && isProfileOwner && (
                <Button
                    component={Link}
                    to="/admin"
                    variant="contained"
                    color="primary"
                    style={{marginBottom: '20px'}}
                >
                    Admin Panel
                </Button>
            )}
            <Typography variant="h5">{t('profile.articles')}:</Typography>
            <List>
                {articles.map(article => (
                    <ListItem key={article.id}>
                        <ListItemText
                            primary={i18n.language === 'en' ? article.title_en : article.title_ua}
                            secondary={i18n.language === 'en' ? article.description_en : article.description_ua}
                        />
                        {(isAdmin || isProfileOwner) && (
                            <>
                                <Button onClick={() => handleDelete(article.id)}>{t('profile.delete')}</Button>
                                <Button component={Link}
                                        to={`/articles/${article.id}/edit`}>{t('profile.edit')}</Button>
                            </>
                        )}
                    </ListItem>
                ))}
            </List>
        </Container>
    );
};

export default Profile;
