import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';

const SearchBar = ({ onSearch }) => {
    const { t } = useTranslation();
    const [searchTerm, setSearchTerm] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [selectedCategory, setSelectedCategory] = useState('');
    const [selectedSource, setSelectedSource] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        onSearch({
            searchTerm,
            startDate,
            endDate,
            selectedCategory,
            selectedSource
        });
    };

    const allCategories = t('articleList.allCategories', { returnObjects: true });
    const allSources = t('articleList.allSources', { returnObjects: true });

    return (
        <form onSubmit={handleSubmit} className="form-inline mb-4">
            <input type="text" className="form-control mr-2" placeholder={t('articleList.searchPlaceholder')} value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} />
            <input type="date" className="form-control mr-2" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
            <input type="date" className="form-control mr-2" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
            <select className="form-control mr-2" value={selectedCategory} onChange={(e) => setSelectedCategory(e.target.value)}>
                {Object.keys(allCategories).map(categoryKey => (
                    <option key={categoryKey} value={categoryKey}>{allCategories[categoryKey]}</option>
                ))}
            </select>
            <select className="form-control mr-2" value={selectedSource} onChange={(e) => setSelectedSource(e.target.value)}>
                {Object.keys(allSources).map(sourceKey => (
                    <option key={sourceKey} value={sourceKey}>{allSources[sourceKey]}</option>
                ))}
            </select>
            <button type="submit" className="btn btn-primary">{t('articleList.search')}</button>
        </form>
    );
};

export default SearchBar;
