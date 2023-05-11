let audioContext = new window.AudioContext();
let source;
let current = null;
let playing = false;

window.addEventListener('load', function (){
    document.getElementById('fibers')
        .addEventListener('click', function (event) {

            if (event.target.className !== 'audio') return;

            let song_id = event.target.id;

            // to prevent any other audio start playing
            if (playing === false) {
                play_audio(song_id);
                current = song_id;
                playing = true;
            } else {
                source.stop(0);
                playing = false;
                if (song_id !== current) {
                    play_audio(song_id);
                    current = song_id;
                    playing = true;
                }
            }
        })
});

async function play_audio(id) {
    await load_audio(id);
    source.start(0);
}

async function load_audio(id) {

    source = audioContext.createBufferSource();
    let parameters = { id : id };

    let url = new URL(location.origin.toString() + '/file');
    url.search = new URLSearchParams(parameters).toString();

    let response = await fetch(url.toString(), {
        method: 'GET',
        headers: {
            'Type': 'ajax'
        }
    });

    let arrayBuffer = await response.arrayBuffer();

    return audioContext.decodeAudioData(arrayBuffer, function(decodedData){
        source.buffer = decodedData;
        source.connect(audioContext.destination);
    })
}