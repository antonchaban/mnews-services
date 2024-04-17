import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {useTranslation} from 'react-i18next';
import SearchBar from './SearchBar';

const ArticleList = () => {
    const {t, i18n} = useTranslation();
    const [articles, setArticles] = useState([]);
    const [selectedArticle, setSelectedArticle] = useState(null); // State to manage selected article for modal
    const [filteredArticles, setFilteredArticles] = useState([]);

    useEffect(() => {
        axios.get('http://localhost/api/articles')
            .then(response => {
                setArticles(response.data);
                setFilteredArticles(response.data); // Initialize filteredArticles with all articles
            })
            .catch(error => {
                console.error('Error fetching articles:', error);
            });
    }, []);

/*    const handleSearch = (searchParams) => {
        const { searchTerm, startDate, endDate, selectedCategory, selectedSource } = searchParams;

        // Filter articles based on search parameters
        let filtered = articles.filter(article => {
            // Check if article title or description contains the search term
            const matchesSearchTerm = article.title_en.toLowerCase().includes(searchTerm.toLowerCase()) || article.description_en.toLowerCase().includes(searchTerm.toLowerCase());

            // Check if article date is within the specified date range
            const articleDate = new Date(article.articleDate);
            const isWithinDateRange = (!startDate || articleDate >= new Date(startDate)) && (!endDate || articleDate <= new Date(endDate));

            // Check if article matches selected category
            const matchesCategory = !selectedCategory || selectedCategory === "ALL_CATEGORIES" || article.categories.includes(selectedCategory);

            // Check if article matches selected source
            const matchesSource = !selectedSource || selectedSource === "ALL_SOURCES" || article.source === selectedSource;

            return matchesSearchTerm && isWithinDateRange && matchesCategory && matchesSource;
        });

        setFilteredArticles(filtered);
    };*/

/*    const handleSearch = (searchParams) => {
        const { searchTerm, startDate, endDate, selectedCategory, selectedSource } = searchParams;

        // Filter articles based on search parameters
        let filtered = articles.filter(article => {
            // Check if article is undefined or any essential property is undefined
            if (!article || !article.title_en || !article.description_en || !article.articleDate || !article.categories || !article.source) {
                // Handle undefined article
                console.log('Undefined article:', article);
                return false; // Exclude undefined article from the filtered list
            }

            // Check if article title or description contains the search term
            const matchesSearchTerm = article.title_en.toLowerCase().includes(searchTerm.toLowerCase()) || article.description_en.toLowerCase().includes(searchTerm.toLowerCase());

            // Check if article date is within the specified date range
            const articleDate = new Date(article.articleDate);
            const isWithinDateRange = (!startDate || articleDate >= new Date(startDate)) && (!endDate || articleDate <= new Date(endDate));

            // Check if article matches selected category
            const matchesCategory = !selectedCategory || selectedCategory === "ALL_CATEGORIES" || article.categories.includes(selectedCategory);

            // Check if article matches selected source
            const matchesSource = !selectedSource || selectedSource === "ALL_SOURCES" || article.source === selectedSource;

            return matchesSearchTerm && isWithinDateRange && matchesCategory && matchesSource;
        });

        setFilteredArticles(filtered);
    };*/

    const handleSearch = (searchParams) => {
        const { searchTerm, startDate, endDate, selectedCategory, selectedSource } = searchParams;

        // Filter articles based on search parameters
        let filtered = articles.filter(article => {
            // Check if article is undefined or any essential property is undefined
            if (!article) {
                console.log('Undefined article:', article);
                return false; // Exclude undefined article from the filtered list
            }

            if (!article.title_en) {
                console.log('Undefined title:', article);
                return false; // Exclude article with undefined title
            }

            if (!article.description_en) {
                console.log('Undefined description:', article);
                return false; // Exclude article with undefined description
            }

            if (!article.articleDate) {
                console.log('Undefined articleDate:', article);
                return false; // Exclude article with undefined articleDate
            }

            if (!article.categories) {
                console.log('Undefined categories:', article);
                return false; // Exclude article with undefined categories
            }

            if (!article.source) {
                console.log('Undefined source:', article);
                return false; // Exclude article with undefined source
            }

            // Check if article title or description contains the search term
            const matchesSearchTerm = article.title_en.toLowerCase().includes(searchTerm.toLowerCase()) || article.description_en.toLowerCase().includes(searchTerm.toLowerCase());

            // Check if article date is within the specified date range
            const articleDate = new Date(article.articleDate);
            const isWithinDateRange = (!startDate || articleDate >= new Date(startDate)) && (!endDate || articleDate <= new Date(endDate));

            // Check if article matches selected category
            const matchesCategory = !selectedCategory || selectedCategory === "ALL_CATEGORIES" || article.categories.includes(selectedCategory);

            // Check if article matches selected source
            const matchesSource = !selectedSource || selectedSource === "ALL_SOURCES" || article.source === selectedSource;

            return matchesSearchTerm && isWithinDateRange && matchesCategory && matchesSource;
        });

        setFilteredArticles(filtered);
    };





    const handleViewMore = (article) => {
        setSelectedArticle(article); // Set selected article for modal
    };

    const handleCloseModal = () => {
        setSelectedArticle(null); // Close modal
    };

    return (
        <div className="container">
            <h1 className="mb-4">{t('articleList.title')}</h1>
            <SearchBar onSearch={handleSearch} />
            <div className="row">
                {filteredArticles.map(article => (
                    <div className="col-md-4 mb-4" key={article.id}>
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">{i18n.language === 'en' ? article.title_en : article.title_ua}</h5>
                                <p className="card-text">{i18n.language === 'en' ? article.description_en : article.description_ua}</p>
                                <p className="card-text">{t('articleList.source')}: {article.source}</p>
                                <p className="card-text">{t('articleList.date')}: {new Date(article.articleDate).toLocaleString()}</p>
                                <button className="btn btn-secondary" onClick={() => handleViewMore(article)}>{t('articleList.viewMore')}</button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            {selectedArticle && (
                <div className="modal fade show" tabIndex="-1" role="dialog" style={{display: 'block'}}>
                    <div className="modal-dialog" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">{i18n.language === 'en' ? selectedArticle.title_en : selectedArticle.title_ua}</h5>
                                <button type="button" className="close" aria-label="Close" onClick={handleCloseModal}>
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>{i18n.language === 'en' ? selectedArticle.description_en : selectedArticle.description_ua}</p>
                                <p>{t('articleList.source')}: {selectedArticle.source}</p>
                                <p>{t('articleList.date')}: {new Date(selectedArticle.articleDate).toLocaleDateString()}</p>
                                <p>{t('articleList.categories')}: {selectedArticle.categories.join(', ')}</p>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary"
                                        onClick={handleCloseModal}>{t('articleList.close')}</button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ArticleList;
