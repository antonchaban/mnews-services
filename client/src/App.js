/*
import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
*/

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

