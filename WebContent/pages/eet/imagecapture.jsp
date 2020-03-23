<div class="modal" tabindex="-1" role="dialog" id="imageCaptureModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Capture Image</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="booth">
                    <video id="video" playsinline autoplay></video>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="snap">Take Photo</button>
            </div>
        </div>
    </div>
</div>

<script>

    'use strict';

    const video = document.getElementById('video');
    const canvas = document.getElementById('canvas');
    const snap = document.getElementById('snap');
    const errorMsgElement = document.getElementById('spanErrorMsg');

    const constraints = {
        audio: false,
        video: {
            width: 200,
            height: 200
        }
    };

    async function init(){
        try{
            const stream = await navigator.mediaDevices.getUserMedia(constraints);
            handleSuccess(stream);
        } catch (e){
            errorMsgElement.innerHTML = 'navigator.getUserMedia.error:${e.toString()}';
        }
    }

    function handleSuccess(stream){
        window.stream = stream;
        video.srcObject = stream;
    }

    init();
    var context = canvas.getContext('2d');
    var imageData;
    var bobImg = new Image();
    snap.addEventListener('click', function(){
        context.drawImage(video);
        var imageHeight = video.height;
        var imageWidth = video.width;
        imageData = context.getImageData(0, 0, imageWidth, imageHeight);
        $('#imageCaptureModal').modal('hide');
        stopRecording();
    }, false);

    function stopRecording(){
        let stream = video.nativeElement.srcObject;
        let tracks = stream.getTracks();

        tracks.forEach(function (track) {
            track.stop();
        });

        video.nativeElement.srcObject = null;
        video.nativeElement.stop();
    }

</script>
