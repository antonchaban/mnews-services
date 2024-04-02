// src/App.js
// src/App.js
import React from 'react';
import Header from './components/Header';
import ArticleList from './components/ArticleList';
import 'bootstrap/dist/css/bootstrap.min.css';

const App = () => {
    return (
        <div>
            <Header /> {/* Include header component */}
            <main>
                <ArticleList /> {/* Main content */}
            </main>
            {/* Other components or elements */}
        </div>
    );
};

export default App;
