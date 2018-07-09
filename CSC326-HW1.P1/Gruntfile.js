module.exports = function(grunt) {

  grunt.initConfig({

	  less: 
	  {
	      development: 
	      {
	        options: 
	        {
	          compress: true,
	          yuicompress: true,
	          optimization: 2
	        },
	        files: 
	        [{
	  				expand: true,
	  				cwd: "bower_components/bootstrap/less",
	  				src: "**/bootstrap.less",
	  				dest: "www/css/",
	  				ext: ".css"
	        },
	        {
	        		expand: true,
	        		cwd: "less",
	  				src: "**/local.less",
	  				dest: "www/css/",
	  				ext: ".css"
	        }
	        ]
	      }
	  },

	  bower_concat:
      {
      		options: 
      		{
      			separator: ';',
      		},
            all: 
            {	src: ['**/*.js'],
                dest: 'www/js/libs.js'
            },
      },

      jshint:
      {
      	target: ['Gruntfile.js'],
      },

	 
  });

  // This will automatically load any grunt plugin you install, such as grunt-contrib-less.
  require('load-grunt-tasks')(grunt);

  grunt.registerTask('validate', 'jshint');
  grunt.registerTask('package', 'bower_concat');
  // Rename task.

};
