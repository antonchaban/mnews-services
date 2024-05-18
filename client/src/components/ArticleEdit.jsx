import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { TextField, Button, Select, MenuItem } from '@mui/material';

const ArticleEdit = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const { t, i18n } = useTranslation();

    const [article, setArticle] = useState({
        id: '',
        link: '',
        source: '',
        category: '',
        title: '',
        description: '',
        language: ''
    });
    const [selectedCategory, setSelectedCategory] = useState('');
    const [selectedSource, setSelectedSource] = useState('');

    const categories = t('articleList.allCategories', { returnObjects: true });
    const sources = t('articleList.allSources', { returnObjects: true });

    useEffect(() => {
        axios.get(`http://localhost/api/articles/${id}`)
            .then(response => {
                const fetchedArticle = response.data;
                setArticle({
                    ...fetchedArticle,
                    title: i18n.language === 'en' ? fetchedArticle.title_en : fetchedArticle.title_ua,
                    description: i18n.language === 'en' ? fetchedArticle.description_en : fetchedArticle.description_ua
                });
                setSelectedCategory(fetchedArticle.category);
                setSelectedSource(fetchedArticle.source);
            })
            .catch(error => {
                console.error('Error fetching article details:', error);
            });
    }, [id, i18n.language]);

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

    const handleUpdateArticle = () => {
        const updatedArticle = {
            id: article.id,
            link: article.link,
            title: article.title,
            description: article.description,
            category: selectedCategory,
            source: selectedSource,
            language: i18n.language
        };

        axios.put(`http://localhost/api/articles/${id}`, updatedArticle)
            .then(response => {
                console.log('Article updated successfully:', response.data);
            })
            .catch(error => {
                console.error('Error updating article:', error);
            });
    };

    return (
        <div>
            <h2>{t('articleEdit.editArticle')}</h2>
            <TextField
                label={t('articleEdit.link')}
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
                <MenuItem value="">{t('articleEdit.selectCategory')}</MenuItem>
                {Object.keys(categories).map(categoryKey => (
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
                <MenuItem value="">{t('articleEdit.selectSource')}</MenuItem>
                {Object.keys(sources).map(sourceKey => (
                    <MenuItem key={sourceKey} value={sourceKey}>{sources[sourceKey]}</MenuItem>
                ))}
            </Select>

            <TextField
                label={t(`articleEdit.title.${i18n.language === 'en' ? 'title_en' : 'title_ua'}`)}
                value={article.title}
                onChange={handleTitleChange}
                fullWidth
                margin="dense"
                variant="outlined"
            />

            <TextField
                label={t(`articleEdit.description.${i18n.language === 'en' ? 'description_en' : 'description_ua'}`)}
                value={article.description}
                onChange={handleDescriptionChange}
                fullWidth
                margin="dense"
                variant="outlined"
                multiline
                rows={4}
            />

            <Button onClick={handleUpdateArticle} variant="contained" color="primary">
                {t('articleEdit.update')}
            </Button>
        </div>
    );
};

export default ArticleEdit;
