import React, { useEffect, useState } from 'react'
import { getRoomById, updateRoom } from '../utils/ApiFunctions'
import { useParams } from 'react-router-dom'

const EditRoom = () => {
    const [room, setRoom]=useState({
        photo: null,
        roomType: "",
        roomPrice: ""
    })

    const [imagePreview, setImagePreview]=useState("")
    const [successMessage,setSuccessMessage] = useState("")
    const [errorMessage,setErrorMessage] = useState("") 

    const {roomId}=useParams()

    const handleInputChange=(e)=>{
        const {name,value}=e.target
        setRoom({...room,[name]:value})
    }

    const handleImageChange=(e)=>{
        const selectedImage=e.target.files[0];
        setRoom({...room,photo:selectedImage});
        setImagePreview(URL.createObjectURL(selectedImage));
    }

    useEffect(()=>{
        const fetchRoom=async ()=>{
            try {
                const roomData=await getRoomById(roomId)
                setRoom(roomData)
                setImagePreview(roomData.photo)
            } catch (error) {
                console.error(error)
            }
        }
        fetchRoom()
    },[roomId])
    
    const handleSubmit=async(e)=>{
        e.preventDefault();

        try {
            const response=await updateRoom(roomId, room)

            if(response.status==200){
                setSuccessMessage("Room updated successfully")
                const updatedRoomData=await getRoomById(roomId)
                setRoom(updatedRoomData)
                setImagePreview(updatedRoomData.photo)
                setErrorMessage("")
            }else{
                setErrorMessage("Error updating room")
            }
        } catch (error) {
            setErrorMessage(error.message);
        }
    }
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
    
                        <div className="mb3">
                            <label htmlFor="roomType" className='form-label'>
                                Room Type
                            </label>
                            <input type='text' className='form-control' id='roomType' name='roomType' value={room.roomType} onChange={handleInputChange}/>
                        </div>
    
                        <div className="mb3">
                            <label htmlFor="roomPrice" className='form-label'>
                                Room Price
                            </label>
                            <input className='form-control' required id='roomPrice' name='roomPrice' type='number' onChange={handleInputChange}/>
                        </div>
                        <div className="mb3">
                            <label htmlFor="photo" className='form-label'>
                                Photo
                            </label>
                            <input required type='file' className='form-control' id='photo' name='photo' onChange={handleImageChange}/>
                            {imagePreview &&(
                                <img src={`data:image/jpeg;base64,${imagePreview}`} alt='Room Preview' style={{maxWidth:"400px", maxHeight:"400px"}} className='mt-3'/>
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
      )
}

export default EditRoom
