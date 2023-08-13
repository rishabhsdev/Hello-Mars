const axios = require('axios');

async function fetchRoverPhotos(rover, sol, apiKey) {
    const url = `https://api.nasa.gov/mars-photos/api/v1/rovers/${rover}/photos`;
    const params = {
        sol,
        api_key: apiKey
    };

    try {
        const response = await axios.get(url, { params });
        const data = response.data;
        return data.photos;
    } catch (error) {
        console.error('Error fetching rover photos:', error);
        return [];
    }
}

function displayPhotos(photos) {
    for (const photo of photos) {
        const imgSrc = photo.img_src;
        console.log(imgSrc);  // Modify this to display the image or perform other actions
    }
}

async function main() {
    const apiKey = 'YOUR_API_KEY';  // Replace with your NASA API key
    const rover = 'curiosity';
    const sol = 1000;  // The Martian sol (day) for which you want to fetch photos

    const photos = await fetchRoverPhotos(rover, sol, apiKey);
    displayPhotos(photos);
}

main();
