$(function(){
    var scene = new THREE.Scene();
    var camera = new THREE.PerspectiveCamera(45, window.innerWidth/window.innerHeight, 0.1, 500);

    var axis = new THREE.AxisHelper(10);
    scene.add(axis);

    var grid = new THREE.GridHelper(50,5, 0xff3300, 0xff3300);
    scene.add(grid);

    //Geometry
    var cubeGeometry = new THREE.BoxGeometry(5,5,5);
    var cubeMaterial = new THREE.MeshLambertMaterial({color:0xff3300});
    var cube = new THREE.Mesh(cubeGeometry, cubeMaterial);

    cube.position.x = 2.5;
    cube.position.y = 4;
    cube.position.z = 2.5;
    cube.castShadow = true;

    scene.add(cube);

    /*datGUI controls object*/
    guiControls = new function(){
        this.rotationX  = 0.0;
        this.rotationY  = 0.0;
        this.rotationZ  = 0.0;

        this.lightX = 127;
        this.lightY = 152;
        this.lightZ = 127;
        this.intensity = 3.8;
        this.distance = 1000;
        this.angle = 1.60;
        this.exponent = 2;
        this.shadowCameraNear = 2;
        this.shadowCameraFar = 434;
        this.shadowCameraFov = 46;
        this.shadowMapWidth=1028;
        this.shadowMapHeight=1028;
        this.shadowBias=0.00;
        this.target = cube;
    }

    var datGUI = new dat.GUI();
    datGUI.add(guiControls, 'rotationX', 0,1);
    datGUI.add(guiControls, 'rotationY', 0,1);
    datGUI.add(guiControls, 'rotationZ', 0,1);
    datGUI.add(guiControls, 'lightX',-60,180);
    datGUI.add(guiControls, 'lightY',0,180);
    datGUI.add(guiControls, 'lightZ',-60,180);

    datGUI.add(guiControls, 'intensity',0.01, 5).onChange(function(value){
        spotLight.intensity = value;
    });
    datGUI.add(guiControls, 'distance',0, 1000).onChange(function(value){
        spotLight.distance = value;
    });
    datGUI.add(guiControls, 'angle',0.001, 1.570).onChange(function(value){
        spotLight.angle = value;
    });
    datGUI.add(guiControls, 'exponent',0 ,50 ).onChange(function(value){
        spotLight.exponent = value;
    });
    datGUI.add(guiControls, 'shadowCameraNear',0,100).name("Near").onChange(function(value){
        spotLight.shadowCamera.near = value;
        spotLight.shadowCamera.updateProjectionMatrix();
    });
    datGUI.add(guiControls, 'shadowCameraFar',0,5000).name("Far").onChange(function(value){
        spotLight.shadowCamera.far = value;
        spotLight.shadowCamera.updateProjectionMatrix();
    });
    datGUI.add(guiControls, 'shadowCameraFov',1,180).name("Fov").onChange(function(value){
        spotLight.shadowCamera.fov = value;
        spotLight.shadowCamera.updateProjectionMatrix();
    });
    datGUI.add(guiControls, 'shadowBias',0,1).onChange(function(value){
        spotLight.shadowBias = value;
        spotLight.shadowCamera.updateProjectionMatrix();
    });
    datGUI.close();

    //Plane
    var planeGeometry = new THREE.PlaneGeometry(30,30,30);
    var planeMaterial = new THREE.MeshLambertMaterial({color:0xffffff});
    var plane = new THREE.Mesh(planeGeometry, planeMaterial);

    plane.rotation.x = -0.5*Math.PI;
    plane.receiveShadow=true;

    scene.add(plane);

    //Camera
    camera.position.x = 40;
    camera.position.y = 40;
    camera.position.z = 40;

    camera.lookAt(scene.position);

    //Light
    spotLight = new THREE.SpotLight(0xffffff);
    spotLight.castShadow = true;
    spotLight.position.set (20, 35, 40);
    spotLight.intensity = guiControls.intensity;
    spotLight.distance = guiControls.distance;
    spotLight.angle = guiControls.angle;
    spotLight.exponent = guiControls.exponent;
    spotLight.shadowCameraNear = guiControls.shadowCameraNear;
    spotLight.shadowCameraFar = guiControls.shadowCameraFar;
    spotLight.shadowCameraFov = guiControls.shadowCameraFov;
    spotLight.shadowBias = guiControls.shadowBias;
    spotLight.target = guiControls.target;

    scene.add(spotLight);

    var spotLightHelper = new THREE.SpotLightHelper( spotLight );
    scene.add( spotLightHelper );

    // Renderer
    var renderer = new THREE.WebGLRenderer({antialias:true});
    renderer.shadowMapEnabled = true;
    renderer.shadowMapSoft = true;

    renderer.setClearColor(0x0000000);
    renderer.setSize(window.innerWidth, window.innerHeight);

    $('#webGLContrainer').append(renderer.domElement);
    renderer.render(scene,camera);

    // Orbit Control
    controls = new THREE.OrbitControls( camera, renderer.domElement );
    controls.addEventListener( 'change', render );
    // Animation
    render();
    function render(){
        cube.rotation.x += guiControls.rotationX;
        cube.rotation.y += guiControls.rotationY;
        cube.rotation.z += guiControls.rotationZ;

        spotLight.position.x = guiControls.lightX;
        spotLight.position.y = guiControls.lightY;
        spotLight.position.z = guiControls.lightZ;

        requestAnimationFrame(render);
        renderer.render(scene,camera);
    }

    $(window).resize(function(){
        var SCREEN_WIDTH = window.innerWidth;
        var SCREEN_HEIGHT = window.innerHeight;

        camera.aspect = SCREEN_WIDTH / SCREEN_HEIGHT;
        camera.updateProjectionMatrix();

        renderer.setSize( SCREEN_WIDTH, SCREEN_HEIGHT );
    });
});