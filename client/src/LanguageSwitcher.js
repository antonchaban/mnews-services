import React from 'react';
import { useTranslation } from 'react-i18next';

const LanguageSwitcher = () => {
    const { i18n } = useTranslation();

    const changeLanguage = (lng) => {
        i18n.changeLanguage(lng);
    };

    return (
        <div className="btn-group" role="group" aria-label="Language Switcher">
            <button type="button" className={`btn ${i18n.language === 'en' ? 'btn-primary' : 'btn-secondary'}`} onClick={() => changeLanguage('en')}>English</button>
            <button type="button" className={`btn ${i18n.language === 'ua' ? 'btn-primary' : 'btn-secondary'}`} onClick={() => changeLanguage('ua')}>Українська</button>
        </div>
    );
};

export default LanguageSwitcher;
