import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/api';
import '../styles/Signup.css';

export const Signup = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        usuario: '',
        password: '',
        perfilId: 3, 
        estado: 1,
        persona: {
            dni: '',
            nombre: '',
            apellido: '',
            email: ''
        }
    });

    const [error, setError] = useState('');
    const [mensaje, setMensaje] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name in formData.persona) {
            setFormData({
                ...formData,
                persona: {
                    ...formData.persona,
                    [name]: value
                }
            });
        } else {
            setFormData({
                ...formData,
                [name]: value
            });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post('/auth/signup', formData);
            setMensaje('Registro exitoso. Ahora puedes iniciar sesión.');
            setError('');
            setTimeout(() => navigate('/login'), 2000);
        } catch (err) {
            console.error(err);
            setError('Error al registrar usuario.');
            setMensaje('');
        }
    };

    return (
        <div className='signup'>
            <h1>Registro</h1>
            <form onSubmit={handleSubmit}>
                <div className='form-group'>
                    <label>Usuario:</label>
                    <input type="text" name="usuario" value={formData.usuario} onChange={handleChange} required />
                </div>
                <div className='form-group'>
                    <label>Contraseña:</label>
                    <input type="password" name="password" value={formData.password} onChange={handleChange} required />
                </div>
                <div className='form-group'>
                    <label>DNI:</label>
                    <input type="text" name="dni" value={formData.persona.dni} onChange={handleChange} required />
                </div>
                <div className='form-group'>
                    <label>Nombre:</label>
                    <input type="text" name="nombre" value={formData.persona.nombre} onChange={handleChange} required />
                </div>
                <div className='form-group'>
                    <label>Apellido:</label>
                    <input type="text" name="apellido" value={formData.persona.apellido} onChange={handleChange} required />
                </div>
                <div className='form-group'>
                    <label>Email:</label>
                    <input type="email" name="email" value={formData.persona.email} onChange={handleChange} required />
                </div>
                <button type="submit">Registrarse</button>
            </form>

            {mensaje && <p style={{ color: 'green' }}>{mensaje}</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};
