// ArticleCreate.jsx
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { TextField, Button, Select, MenuItem } from '@mui/material';

const ArticleCreate = () => {
    const navigate = useNavigate();
    const { t, i18n } = useTranslation();

    const [article, setArticle] = useState({
        link: '',
        source: '',
        category: '',
        title: '',
        description: '',
        language: i18n.language
    });
    const [selectedCategory, setSelectedCategory] = useState('');
    const [selectedSource, setSelectedSource] = useState('');

    const categories = t('articleList.allCategories', { returnObjects: true });
    const sources = t('articleList.allSources', { returnObjects: true });

    const handleCategoryChange = (event) => {
        setSelectedCategory(event.target.value);
    };

    const handleSourceChange = (event) => {
        setSelectedSource(event.target.value);
    };

    const handleTitleChange = (e) => {
        setArticle(prevState => ({ ...prevState, title: e.target.value }));
    };

    const handleDescriptionChange = (e) => {
        setArticle(prevState => ({ ...prevState, description: e.target.value }));
    };

    const handleLinkChange = (e) => {
        setArticle(prevState => ({ ...prevState, link: e.target.value }));
    };

    const handleCreateArticle = () => {
        const newArticle = {
            link: article.link,
            title: article.title,
            description: article.description,
            category: selectedCategory,
            source: selectedSource,
            language: i18n.language
        };

        axios.post('http://localhost/api/articles', newArticle)
            .then(response => {
                console.log('Article created successfully:', response.data);
                navigate('/articles');
            })
            .catch(error => {
                console.error('Error creating article:', error);
            });
    };

    return (
        <div>
            <h2>{t('articleCreate.createArticle')}</h2>
            <TextField
                label={t('articleCreate.link')}
                value={article.link}
                onChange={handleLinkChange}
                fullWidth
                margin="dense"
                variant="outlined"
            />
            <Select
                value={selectedCategory}
                onChange={handleCategoryChange}
                fullWidth
                margin="dense"
                variant="outlined"
            >
                <MenuItem value="">{t('articleCreate.selectCategory')}</MenuItem>
                {Object.keys(categories).filter(categoryKey => categoryKey !== 'ALL_CATEGORIES').map(categoryKey => (
                    <MenuItem key={categoryKey} value={categoryKey}>{categories[categoryKey]}</MenuItem>
                ))}
            </Select>
            <Select
                value={selectedSource}
                onChange={handleSourceChange}
                fullWidth
                margin="dense"
                variant="outlined"
            >
                <MenuItem value="">{t('articleCreate.selectSource')}</MenuItem>
                {Object.keys(sources).filter(sourceKey => sourceKey !== 'ALL_SOURCES').map(sourceKey => (
                    <MenuItem key={sourceKey} value={sourceKey}>{sources[sourceKey]}</MenuItem>
                ))}
            </Select>

            <TextField
                label={t(`articleCreate.title.title_${i18n.language}`)}
                value={article.title}
                onChange={handleTitleChange}
                fullWidth
                margin="dense"
                variant="outlined"
            />

            <TextField
                label={t(`articleCreate.description.description_${i18n.language}`)}
                value={article.description}
                onChange={handleDescriptionChange}
                fullWidth
                margin="dense"
                variant="outlined"
                multiline
                rows={4}
            />

            <Button onClick={handleCreateArticle} variant="contained" color="primary">
                {t('articleCreate.create')}
            </Button>
        </div>
    );
};

export default ArticleCreate;
