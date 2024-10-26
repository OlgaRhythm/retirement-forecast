import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage/HomePage'

function App() {
  return (
    <div className="App">
      {/* BrowserRouter is used if multiple pages are needed */}
      <BrowserRouter>
        <Routes>
          {/* Home page */}
          <Route path="*" element={<HomePage />} />
  
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
