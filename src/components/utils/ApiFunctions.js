import axios from "axios";

export const api= axios.create({
    baseURL:"http://localhost:9192"
});
//add room
export async function addRoom(photo, roomType, roomPrice){
    const formData=new FormData();

    formData.append("photo",photo);
    formData.append( "roomType",roomType);
    formData.append("roomPrice",roomPrice);

    const response=await api.post("/rooms/add/new-room",formData);

    if(response.status===201){
        return true;
    }else{
        return false;
    }
}
//get room by room type
export async function getRoomType() {
    try {
        const response = await api.get("/rooms/room-types");
        console.log(response)
        return response.data;
        
    } catch (error) {
        throw new Error("Error fetching room type");
    }
}
// get all rooms
export async function getAllRooms(){
    try {
        const result=await api.get("/rooms/all-rooms");
        return result.data;
    } catch (error) {
        throw new Error("Error fetching rooms");
    }
}
//delete room by id
export async function deleteRoom(roomId){
    try {
        const result=await api.get(`rooms/delete/room/${roomId}`)
        return result.data
    } catch (error) {
        throw new Error(`Error deleting room ${error.message}`);
    }
}