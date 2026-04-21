<?php

//Begin Really Simple Security session cookie settings
@ini_set('session.cookie_httponly', true);
@ini_set('session.cookie_secure', true);
@ini_set('session.use_only_cookies', true);
//END Really Simple Security cookie settings
//Begin Really Simple Security key
define('RSSSL_KEY', 'KrLnmaxpA1ximuAmz2DwQgLMInGqtzk6GDjh84RGobiKlhond0butUzNXyI7zw5o');
//END Really Simple Security key
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the installation.
 * You don't have to use the website, you can copy this file to "wp-config.php"
 * and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * Database settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://developer.wordpress.org/advanced-administration/wordpress/wp-config/
 *
 * @package WordPress
 */

// ** Database settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define( 'DB_NAME', 'gus56791_wp247' );

/** Database username */
define( 'DB_USER', 'gus56791_wp247' );

/** Database password */
define( 'DB_PASSWORD', 'S9z7)qp1!P' );

/** Database hostname */
define( 'DB_HOST', 'localhost' );

/** Database charset to use in creating database tables. */
define( 'DB_CHARSET', 'utf8' );

/** The database collate type. Don't change this if in doubt. */
define( 'DB_COLLATE', '' );

/**#@+
 * Authentication unique keys and salts.
 *
 * Change these to different unique phrases! You can generate these using
 * the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}.
 *
 * You can change these at any point in time to invalidate all existing cookies.
 * This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define( 'AUTH_KEY',         'cza5uexv9yqumryj60shefpgriafyqttjwexz47hwnclkujj1negrszjg33tstb9' );
define( 'SECURE_AUTH_KEY',  'ylqd5f2fkskcmlqtecpknx53yi6aczbon4hnauzjqrnfv6msksq4vzyn3gfzeesx' );
define( 'LOGGED_IN_KEY',    'hz1l3lylgfmiukn4jodeeqqhf9xqsyhxn46onngduio9wr2diwewjpumyedeb8ah' );
define( 'NONCE_KEY',        'wl6kissjjqovlq673uvuep00btznwfbf81uqamjprhdltojzj3wcduztsv2phusp' );
define( 'AUTH_SALT',        'ksf8krj7rqxugt5zpgqe92qy55il94zbnt2nbldpndx7awqckpnkhj8wevcexxit' );
define( 'SECURE_AUTH_SALT', 'qw07lmxmjm0c2zbjuxb3qmiqmw0zlq4onmu1acm0xkgpqicclq1ynfwjulph0epu' );
define( 'LOGGED_IN_SALT',   'q2ivl8dmhtdrnr8gbhhewxpscwtyi7jhw937o3jpkolqayfdx08eojohplwlamob' );
define( 'NONCE_SALT',       'u161hji1vwe4loexm9bhbiyfgixoh1qfktizlq5x897nzk78x45aee8iaiscgleo' );

/**#@-*/

/**
 * WordPress database table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 *
 * At the installation time, database tables are created with the specified prefix.
 * Changing this value after WordPress is installed will make your site think
 * it has not been installed.
 *
 * @link https://developer.wordpress.org/advanced-administration/wordpress/wp-config/#table-prefix
 */
$table_prefix = 'wpgk_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the documentation.
 *
 * @link https://developer.wordpress.org/advanced-administration/debug/debug-wordpress/
 */
define( 'WP_DEBUG', false );

/* Add any custom values between this line and the "stop editing" line. */



/* That's all, stop editing! Happy publishing. */

/** Absolute path to the WordPress directory. */
if ( ! defined( 'ABSPATH' ) ) {
	define( 'ABSPATH', __DIR__ . '/' );
}

/** Sets up WordPress vars and included files. */
require_once ABSPATH . 'wp-settings.php';
