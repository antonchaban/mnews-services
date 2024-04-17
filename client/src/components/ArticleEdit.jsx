import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { Container, Typography, TextField, Button } from '@mui/material';

const ArticleEdit = () => {
    const { id } = useParams();
    const [article, setArticle] = useState(null);
    const { t, i18n } = useTranslation();

    useEffect(() => {
        axios.get(`http://localhost/api/articles/${id}`)
            .then(response => {
                setArticle(response.data);
            })
            .catch(error => {
                console.error('Error fetching article:', error);
            });
    }, [id]);

    const handleChange = (event) => {
        const { name, value } = event.target;
        setArticle(prevArticle => ({
            ...prevArticle,
            [name]: value
        }));
    };

    const handleSubmit = () => {
        // Implement your submit logic here
    };

    if (!article) {
        return <Typography>Loading...</Typography>;
    }

    return (
        <Container>
            <Typography variant="h2">{t('articleEdit.title')}</Typography>
            <TextField
                name={i18n.language === 'en' ? 'title_en' : 'title_ua'}
                label={t('articleEdit.title')}
                value={article[i18n.language === 'en' ? 'title_en' : 'title_ua']}
                onChange={handleChange}
                fullWidth
                margin="normal"
                variant="outlined"
            />
            <TextField
                name={i18n.language === 'en' ? 'description_en' : 'description_ua'}
                label={t('articleEdit.description')}
                value={article[i18n.language === 'en' ? 'description_en' : 'description_ua']}
                onChange={handleChange}
                fullWidth
                multiline
                rows={4}
                margin="normal"
                variant="outlined"
            />
            <Button onClick={handleSubmit} variant="contained" color="primary">{t('articleEdit.save')}</Button>
        </Container>
    );
};

export default ArticleEdit;
