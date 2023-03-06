import { RouterProvider } from 'react-router-dom';
import './App.css'
import { router } from './router';
import { GlobalUserProvider } from './context/user.context';

function App() {
  return (

    <div className='app-container'>
    <GlobalUserProvider>
      <RouterProvider router={router} />
    </GlobalUserProvider>
    </div>
  )
}

export default App;
