import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { Button, MenuItem, Select, TextField } from '@mui/material';

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
    const [errors, setErrors] = useState({});

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

    const handleChange = (e) => {
        const { name, value } = e.target;
        setArticle(prevState => ({ ...prevState, [name]: value }));
    };

    const validate = () => {
        let tempErrors = {};
        tempErrors.link = article.link ? "" : t('articleEdit.error.link');
        tempErrors.category = selectedCategory ? "" : t('articleEdit.error.category');
        tempErrors.source = selectedSource ? "" : t('articleEdit.error.source');
        tempErrors.title = article.title ? "" : t('articleEdit.error.title');
        tempErrors.description = article.description ? "" : t('articleEdit.error.description');
        setErrors(tempErrors);
        return Object.values(tempErrors).every(x => x === "");
    };

    const handleUpdateArticle = () => {
        if (validate()) {
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
                    navigate('/articles');
                })
                .catch(error => {
                    console.error('Error updating article:', error);
                });
        }
    };

    return (
        <div>
            <h2>{t('articleEdit.editArticle')}</h2>
            <TextField
                label={t('articleEdit.link')}
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
                <MenuItem value="">{t('articleEdit.selectCategory')}</MenuItem>
                {Object.keys(categories).map(categoryKey => (
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
                <MenuItem value="">{t('articleEdit.selectSource')}</MenuItem>
                {Object.keys(sources).map(sourceKey => (
                    <MenuItem key={sourceKey} value={sourceKey}>{sources[sourceKey]}</MenuItem>
                ))}
            </Select>
            <p style={{ color: 'red', margin: '4px 0' }}>{errors.source}</p>
            <TextField
                label={t(`articleEdit.title.${i18n.language === 'en' ? 'title_en' : 'title_ua'}`)}
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
                label={t(`articleEdit.description.${i18n.language === 'en' ? 'description_en' : 'description_ua'}`)}
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

            <Button onClick={handleUpdateArticle} variant="contained" color="primary">
                {t('articleEdit.update')}
            </Button>
        </div>
    );
};

export default ArticleEdit;
