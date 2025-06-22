import { useContext } from 'react';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import './App.css';
import { Footer } from './components/Footer';
import { Navbar } from './components/Navbar';
import { AuthContext, AuthProvider } from './context/AuthContext';
import AdminDashboard from './pages/AdminDashboard';
import { Carrito } from './pages/Carrito';
import { Catalogo } from './pages/Catalogo';
import { Login } from './pages/Login';
import { Signin } from './pages/Signin';

const PrivateRoute = ({ children, requiredProfileId }) => {
    const { isAuthenticated, user } = useContext(AuthContext);

    if (!isAuthenticated) {
        return <Navigate to="/login" />;
    }

    if (requiredProfileId && user.perfilId !== requiredProfileId) {
        // return <Navigate to="/" />;
    }

    return children;
};

function App() {
    return (
        <AuthProvider>
        <BrowserRouter>
            <Navbar></Navbar>
            <Routes>
            <Route path="/carrito" element={<Carrito />} />
            <Route path="/catalogo" element={<Catalogo />} />
            <Route path="/signin" element={<Signin />} />
            <Route path="/login" element={<Login />} />
            <Route
                path="/admin/productos"
                element={
                <PrivateRoute requiredProfileId={1}>
                    <AdminDashboard />
                </PrivateRoute>
                }
            />
            <Route path="*" element={<Navigate to="/" />} />
            </Routes>
            <Footer></Footer>
        </BrowserRouter>
        </AuthProvider>
    );
}

export default App;