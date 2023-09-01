import './App.css';
import AppBar from './components/Appbar';
import Music from './components/Music';
import GetRecommendation from './components/GetRecommendation';

function App() {
  return (
    <div className="App">
      <AppBar/>
      <Music/>
      <GetRecommendation/>
    </div>
  );
}

export default App;
