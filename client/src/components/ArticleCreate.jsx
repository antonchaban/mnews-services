import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { Button, MenuItem, Select, TextField } from '@mui/material';

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
    const [errors, setErrors] = useState({});

    const categories = t('articleList.allCategories', { returnObjects: true });
    const sources = t('articleList.allSources', { returnObjects: true });

    const handleCategoryChange = (event) => {
        setSelectedCategory(event.target.value);
    };

    const handleSourceChange = (event) => {
        setSelectedSource(event.target.value);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setArticle(prevState => ({ ...prevState, [name]: value }));
    };

    const validate = () => {
        let tempErrors = {};
        tempErrors.link = article.link ? "" : t('articleCreate.error.link');
        tempErrors.category = selectedCategory ? "" : t('articleCreate.error.category');
        tempErrors.source = selectedSource ? "" : t('articleCreate.error.source');
        tempErrors.title = article.title ? "" : t('articleCreate.error.title');
        tempErrors.description = article.description ? "" : t('articleCreate.error.description');
        setErrors(tempErrors);
        return Object.values(tempErrors).every(x => x === "");
    };

    const handleCreateArticle = () => {
        if (validate()) {
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
        }
    };

    return (
        <div>
            <h2>{t('articleCreate.createArticle')}</h2>
            <TextField
                label={t('articleCreate.link')}
                name="link"
                value={article.link}
                onChange={handleChange}
                fullWidth
                margin="dense"
                variant="outlined"
                error={!!errors.link}
                helperText={errors.link}
            />
            <Select
                value={selectedCategory}
                onChange={handleCategoryChange}
                fullWidth
                margin="dense"
                variant="outlined"
                error={!!errors.category}
                displayEmpty
            >
                <MenuItem value="">{t('articleCreate.selectCategory')}</MenuItem>
                {Object.keys(categories).filter(categoryKey => categoryKey !== 'ALL_CATEGORIES').map(categoryKey => (
                    <MenuItem key={categoryKey} value={categoryKey}>{categories[categoryKey]}</MenuItem>
                ))}
            </Select>
            <p style={{ color: 'red', margin: '4px 0' }}>{errors.category}</p>
            <Select
                value={selectedSource}
                onChange={handleSourceChange}
                fullWidth
                margin="dense"
                variant="outlined"
                error={!!errors.source}
                displayEmpty
            >
                <MenuItem value="">{t('articleCreate.selectSource')}</MenuItem>
                {Object.keys(sources).filter(sourceKey => sourceKey !== 'ALL_SOURCES').map(sourceKey => (
                    <MenuItem key={sourceKey} value={sourceKey}>{sources[sourceKey]}</MenuItem>
                ))}
            </Select>
            <p style={{ color: 'red', margin: '4px 0' }}>{errors.source}</p>
            <TextField
                label={t(`articleCreate.title.title_${i18n.language}`)}
                name="title"
                value={article.title}
                onChange={handleChange}
                fullWidth
                margin="dense"
                variant="outlined"
                error={!!errors.title}
                helperText={errors.title}
            />
            <TextField
                label={t(`articleCreate.description.description_${i18n.language}`)}
                name="description"
                value={article.description}
                onChange={handleChange}
                fullWidth
                margin="dense"
                variant="outlined"
                multiline
                rows={4}
                error={!!errors.description}
                helperText={errors.description}
            />

            <Button onClick={handleCreateArticle} variant="contained" color="primary">
                {t('articleCreate.create')}
            </Button>
        </div>
    );
};

export default ArticleCreate;
