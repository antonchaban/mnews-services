import i18n from 'i18next';
import {initReactI18next} from 'react-i18next';
import enTranslation from './locales/en.json';
import uaTranslation from './locales/ua.json';

i18n
    .use(initReactI18next)
    .init({
        resources: {
            en: {translation: enTranslation},
            ua: {translation: uaTranslation}
        },
        lng: 'en',
        fallbackLng: 'en',
        interpolation: {
            escapeValue: false
        }
    });

export default i18n;
