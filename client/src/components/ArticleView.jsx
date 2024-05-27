import React, { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useTranslation } from 'react-i18next';
import { Container, Typography, Button } from '@mui/material';

const ArticleView = () => {
    const { id } = useParams();
    const [article, setArticle] = useState(null);
    const [user, setUser] = useState(null);
    const [cookies] = useCookies(['USER_ID']);
    const navigate = useNavigate();
    const { t, i18n } = useTranslation();

    useEffect(() => {
        axios.get(`http://localhost/api/articles/${id}`)
            .then(response => {
                setArticle(response.data);
            })
            .catch(error => {
                console.error('Error fetching article details:', error);
            });

        if (cookies.USER_ID) {
            axios.get(`http://localhost/api/users/${cookies.USER_ID}`)
                .then(response => {
                    setUser(response.data);
                })
                .catch(error => {
                    console.error('Error fetching user details:', error);
                });
        }
    }, [id, cookies.USER_ID]);

    const handleDelete = () => {
        axios.delete(`http://localhost/api/articles/${id}`)
            .then(response => {
                console.log('Article deleted successfully:', response.data);
                navigate('/articles');
            })
            .catch(error => {
                console.error('Error deleting article:', error);
            });
    };

    if (!article || !user) {
        return <Typography>Loading...</Typography>;
    }

    const isAuthor = article.user.id === parseInt(cookies.USER_ID);
    const isAdmin = user.roles.includes('ROLE_ADMIN');

    return (
        <Container>
            <Typography variant="h2">{i18n.language === 'en' ? article.title_en : article.title_ua}</Typography>
            <Typography variant="body1">{i18n.language === 'en' ? article.description_en : article.description_ua}</Typography>
            <Typography variant="body2">{t('articleList.source')}: {article.source}</Typography>
            <Typography variant="body2">{t('articleList.date')}: {new Date(article.articleDate).toLocaleString()}</Typography>
            <Typography variant="body2">{t('articleList.categories')}: {article.categories.join(', ')}</Typography>
            <Typography variant="body2">
                {t('articleList.author')}: <Link to={`/profile/${article.user.id}`}>{article.user.username}</Link>
            </Typography>

            {(isAdmin || isAuthor) && (
                <div>
                    <Button
                        component={Link}
                        to={`/articles/${article.id}/edit`}
                        variant="contained"
                        color="primary"
                        style={{ marginRight: '10px' }}
                    >
                        {t('articleList.edit')}
                    </Button>
                    <Button
                        onClick={handleDelete}
                        variant="contained"
                        color="secondary"
                    >
                        {t('articleList.delete')}
                    </Button>
                </div>
            )}
        </Container>
    );
};

export default ArticleView;
