import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useTranslation } from 'react-i18next';
import SearchBar from './SearchBar';
import { useNavigate } from 'react-router-dom';

const ArticleList = () => {
    const { t, i18n } = useTranslation();
    const [articles, setArticles] = useState([]);
    const [filteredArticles, setFilteredArticles] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('http://localhost/api/articles')
            .then(response => {
                setArticles(response.data);
                setFilteredArticles(response.data);
            })
            .catch(error => {
                console.error('Error fetching articles:', error);
            });
    }, []);

    const handleSearch = (searchParams) => {
        const { searchTerm, startDate, endDate, selectedCategory, selectedSource } = searchParams;
        let filtered = articles.filter(article => {
            if (!article || !article.title_en || !article.description_en || !article.articleDate || !article.categories || !article.source) {
                return false;
            }
            const matchesSearchTerm = article.title_en.toLowerCase().includes(searchTerm.toLowerCase()) || article.description_en.toLowerCase().includes(searchTerm.toLowerCase());
            const articleDate = new Date(article.articleDate);
            const isWithinDateRange = (!startDate || articleDate >= new Date(startDate)) && (!endDate || articleDate <= new Date(endDate));
            const matchesCategory = !selectedCategory || selectedCategory === "ALL_CATEGORIES" || article.categories.includes(selectedCategory);
            const matchesSource = !selectedSource || selectedSource === "ALL_SOURCES" || article.source === selectedSource;
            return matchesSearchTerm && isWithinDateRange && matchesCategory && matchesSource;
        });
        setFilteredArticles(filtered);
    };

    const handleViewMore = (id) => {
        navigate(`/articles/${id}`);
    };

    return (
        <div className="container">
            <h1 className="mb-4">{t('articleList.title')}</h1>
            <SearchBar onSearch={handleSearch} />
            <div className="row">
                {filteredArticles.length > 0 ? (
                    filteredArticles.map(article => (
                        <div className="col-md-4 mb-4" key={article.id}>
                            <div className="card">
                                <div className="card-body">
                                    <h5 className="card-title">{i18n.language === 'en' ? article.title_en : article.title_ua}</h5>
                                    <p className="card-text">{i18n.language === 'en' ? article.description_en : article.description_ua}</p>
                                    <p className="card-text">{t('articleList.source')}: {article.source}</p>
                                    <p className="card-text">{t('articleList.date')}: {new Date(article.articleDate).toLocaleString()}</p>
                                    <button className="btn btn-secondary"
                                            onClick={() => handleViewMore(article.id)}>{t('articleList.viewMore')}</button>
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <div className="col-12">
                        <p className="text-center mt-4">{t('articleList.noArticles')}</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default ArticleList;
