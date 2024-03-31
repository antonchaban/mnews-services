import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {useTranslation} from 'react-i18next';

const ArticleList = () => {
    const {t, i18n} = useTranslation();
    const [articles, setArticles] = useState([]);
    const [selectedArticle, setSelectedArticle] = useState(null); // State to manage selected article for modal

    useEffect(() => {
        axios.get('http://localhost/api/articles')
            .then(response => {
                // Sort articles by date from newest to oldest
                const sortedArticles = response.data.sort((a, b) => new Date(b.articleDate) - new Date(a.articleDate));
                setArticles(sortedArticles);
            })
            .catch(error => {
                console.error('Error fetching articles:', error);
            });
    }, []);

    const handleViewMore = (article) => {
        setSelectedArticle(article); // Set selected article for modal
    };

    const handleCloseModal = () => {
        setSelectedArticle(null); // Close modal
    };

    return (
        <div className="container">
            <h1 className="mb-4">{t('articleList.title')}</h1>
            <div className="row">
                {articles.map(article => (
                    <div className="col-md-4 mb-4" key={article.id}>
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">{i18n.language === 'en' ? article.title_en : article.title_ua}</h5>
                                <p className="card-text">{i18n.language === 'en' ? article.description_en.slice(0, 100) : article.description_ua.slice(0, 100)}</p>
                                <p className="card-text">{t('articleList.source')}: {article.source}</p>
                                <p className="card-text">{t('articleList.date')}: {new Date(article.articleDate).toLocaleString()}</p> {/* Include time */}
                                <button className="btn btn-secondary" onClick={() => handleViewMore(article)}>{t('articleList.viewMore')}</button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            {/* Implement modal logic here */}
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
