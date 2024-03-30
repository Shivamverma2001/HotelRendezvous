import React, { useEffect, useState } from 'react';
import { getRoomById, updateRoom } from '../utils/ApiFunctions';
import { useParams } from 'react-router-dom';

const EditRoom = () => {
    const [room, setRoom] = useState({
        photo: null,
        roomType: "",
        roomPrice: ""
    });
    const [imagePreview, setImagePreview] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const { roomId } = useParams();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setRoom({ ...room, [name]: value });
    };

    const handleImageChange = (e) => {
        const selectedImage = e.target.files[0];
        setRoom({ ...room, photo: selectedImage });
        setImagePreview(URL.createObjectURL(selectedImage));
    };

    useEffect(() => {
        const fetchRoom = async () => {
            try {
                const roomData = await getRoomById(roomId);
                console.log("Room Data from API:", roomData); // Log room data received from API
                setRoom(roomData);
                console.log("Room Data after setRoom:", room); // Log room data after updating state
                if (roomData.photo) {
                    const blob = new Blob([roomData.photo], { type: 'image/jpeg' });
                    const url = URL.createObjectURL(blob);
                    setImagePreview(url);
                    console.log("Image URL:", url); // Log image URL
                }
            } catch (error) {
                console.error(error);
            }
        };
        fetchRoom();
    }, [roomId]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await updateRoom(roomId, room);
            console.log("Update Room Response:", response); // Log response from updateRoom function
            if (response.status === 200) {
                setSuccessMessage("Room updated successfully");
                const updatedRoomData = await getRoomById(roomId);
                setRoom(updatedRoomData);
                if (updatedRoomData.photo) {
                    const blob = new Blob([updatedRoomData.photo], { type: 'image/jpeg' });
                    const url = URL.createObjectURL(blob);
                    setImagePreview(url);
                    console.log("Updated Image URL:", url); // Log updated image URL
                }
                setErrorMessage("");
            } else {
                setErrorMessage("Error updating room");
            }
        } catch (error) {
            setErrorMessage(error.message);
        }
    };

    return (
        <>
            <section className='container mt-5 mb-5'>
                <div className='row justify-content-center'>
                    <div className='col-md-8 col-lg-6'>
                        <h2 className='mt-5 mb-2'>Add a new Room</h2>
                        {successMessage && (
                            <div className='alert alert-success fade show'>{successMessage}</div>
                        )}
                        {errorMessage && (
                            <div className='alert alert-danger fade show'>{errorMessage}</div>
                        )}
                        <form onSubmit={handleSubmit}>
                            <div className="mb-3">
                                <label htmlFor="roomType" className='form-label'>
                                    Room Type
                                </label>
                                <input type='text' className='form-control' id='roomType' name='roomType' value={room.roomType} onChange={handleInputChange} />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="roomPrice" className='form-label'>
                                    Room Price
                                </label>
                                <input className='form-control' required id='roomPrice' name='roomPrice' value={room.roomPrice} type='number' onChange={handleInputChange} />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="photo" className='form-label hotel-color'>
                                    Photo
                                </label>
                                <input required type='file' className='form-control' id='photo' name='photo' onChange={handleImageChange} />
                                {imagePreview && (
                                    <img src={imagePreview} alt='Room Preview' style={{ maxWidth: "400px", maxHeight: "400px" }} className='mt-3' />
                                )}
                            </div>
                            <div className='d-grid d-md-flex mt-2'>
                                <button className='btn btn-outline-primary ml-5' type='submit'>Save Room</button>
                            </div>
                        </form>
                    </div>
                </div>
            </section>
        </>
    );
}

export default EditRoom;
