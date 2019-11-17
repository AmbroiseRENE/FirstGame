package jeux1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Course extends JFrame {

	// dimension d l'ecran
	final int LARGEUR = 900, HAUTEUR = 650;

	// vitesse des joueurs
	double vitesseJ1 = .5, vitesseJ2 = .5;

	// Constante de directions
	final int HAUT = 0, DROITE = 1, BAS = 2, GAUCHE = 3;

	// garde la trace de la direction
	int directionJ1 = HAUT;
	int directionJ2 = HAUT;

	// creation des contours du circuit
	Rectangle gauche = new Rectangle(0, 0, LARGEUR / 9, HAUTEUR);
	Rectangle droite = new Rectangle((LARGEUR / 9) * 8, 0, LARGEUR / 9, HAUTEUR);
	Rectangle haut = new Rectangle(0, 0, LARGEUR, HAUTEUR / 9);
	Rectangle bas = new Rectangle(0, (HAUTEUR / 9) * 8, LARGEUR, HAUTEUR / 9);
	Rectangle centre = new Rectangle((int) ((LARGEUR / 9) * 2.5), (int) ((HAUTEUR / 9) * 2.5),
			(int) ((LARGEUR / 9) * 5), (HAUTEUR / 9) * 4);

	Rectangle obst1 = new Rectangle(LARGEUR / 2, (int) ((HAUTEUR / 9) * 7), LARGEUR / 10, HAUTEUR / 9);
	Rectangle obst2 = new Rectangle(LARGEUR / 3, (int) ((HAUTEUR / 9) * 5), LARGEUR / 10, HAUTEUR / 4);
	Rectangle obst3 = new Rectangle(2 * (LARGEUR / 3), (int) ((HAUTEUR / 9) * 5), LARGEUR / 10, HAUTEUR / 4);
	Rectangle obst4 = new Rectangle(LARGEUR / 3, (HAUTEUR / 9), LARGEUR / 30, HAUTEUR / 9);
	Rectangle obst5 = new Rectangle(LARGEUR / 2, (int) ((HAUTEUR / 9) * 1.5), LARGEUR / 30, HAUTEUR / 4);

	Rectangle arrivee = new Rectangle(LARGEUR / 9, (HAUTEUR / 2) - HAUTEUR / 9, (int) ((LARGEUR / 9) * 1.5),
			HAUTEUR / 70);

	Rectangle ligne0 = new Rectangle(LARGEUR / 9, HAUTEUR / 2, (int) ((LARGEUR / 19) * 1.5), HAUTEUR / 140);
	Rectangle ligne1 = new Rectangle(((LARGEUR / 9) + ((int) ((LARGEUR / 9) * 1.5) / 2)),
			(HAUTEUR / 2) + (HAUTEUR / 10), (int) ((LARGEUR / 9) * 1.5) / 2, HAUTEUR / 140);

	Rectangle j1 = new Rectangle(LARGEUR / 9, HAUTEUR / 2, LARGEUR / 30, LARGEUR / 30);
	Rectangle j2 = new Rectangle(((LARGEUR / 9) + ((int) ((LARGEUR / 9) * 1.5) / 2)), (HAUTEUR / 2) + (HAUTEUR / 10),
			LARGEUR / 30, LARGEUR / 30);

	// constructeur
	public Course() {
		super("La course du destin");
		setSize(LARGEUR, HAUTEUR);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		Mouvement1 m1 = new Mouvement1();
		Mouvement2 m2 = new Mouvement2();
		m1.start();
		m2.start();
	}

	// dessine les voitures et la piste
	public void paint(Graphics g) {
		super.paint(g);
		// fond de la piste
		g.setColor(Color.DARK_GRAY);

		// bordure de ce qu'on dessine
		g.fillRect(0, 0, LARGEUR, HAUTEUR);
		g.setColor(Color.GREEN);

		// dessin de la piste
		g.fillRect(gauche.x, gauche.y, gauche.width, gauche.height);
		g.fillRect(droite.x, droite.y, droite.width, droite.height);
		g.fillRect(bas.x, bas.y, bas.width, bas.height);
		g.fillRect(haut.x, haut.y, haut.width, haut.height);
		g.fillRect(centre.x, centre.y, centre.width, centre.height);
		g.fillRect(obst1.x, obst1.y, obst1.width, obst1.height);
		g.fillRect(obst2.x, obst2.y, obst2.width, obst2.height);
		g.fillRect(obst3.x, obst3.y, obst3.width, obst3.height);
		g.fillRect(obst4.x, obst4.y, obst4.width, obst4.height);
		g.fillRect(obst5.x, obst5.y, obst5.width, obst5.height);

		// ligne de dep rt
		g.setColor(Color.WHITE);
		g.fillRect(ligne0.x, ligne0.y, ligne0.width, ligne0.height);
		g.fillRect(ligne1.x, ligne1.y, ligne1.width, ligne1.height);

		// ligne d'arrivée
		g.setColor(Color.YELLOW);
		g.fillRect(arrivee.x, arrivee.y, arrivee.width, arrivee.height);

		// joueur 1
		g.setColor(Color.BLUE);
		g.fill3DRect(j1.x, j1.y, j1.width, j1.height, true);

		// joueur 2

		g.setColor(Color.RED);
		g.fill3DRect(j2.x, j2.y, j2.width, j2.height, true);
	}

	public class Mouvement1 extends Thread implements KeyListener {
		public void run() {

			// activer Keylistener
			addKeyListener(this);
			// boucle infini
			while (true) {
				// arret en cas d'erreur
				try {
					// refresh
					repaint();
					// Si la voiture touche un mur sa vitesse diminue
					if (j1.intersects(gauche) || j1.intersects(droite) || j1.intersects(haut) || j1.intersects(bas)
							|| j1.intersects(obst1) || j1.intersects(obst2) || j1.intersects(obst3)
							|| j1.intersects(obst4) || j1.intersects(j2) || j1.intersects(obst5)) {
						vitesseJ1 = -4;
					}
					if (j1.intersects(centre)) {
						vitesseJ1 = -2.5;
					}
					// augmentation de la vitesse
					if (vitesseJ1 <= 5)
						vitesseJ1 += .2;

					// deplacement en fonction de la direction
					if (directionJ1 == HAUT) {
						j1.y -= (int) vitesseJ1;
					}
					if (directionJ1 == BAS) {
						j1.y += (int) vitesseJ1;
					}
					if (directionJ1 == GAUCHE) {
						j1.x -= (int) vitesseJ1;
					}
					if (directionJ1 == DROITE) {
						j1.x += (int) vitesseJ1;
					}
					
					Thread.sleep(75);
				} catch (Exception e) {
					break;
				}
			}
		}

		// pour le key listener
		public void keyPressed(KeyEvent event) {

		}

		// pour le key listener
		public void keyReleased(KeyEvent event) {
			// TODO Auto-generated method stub

		}

		// pour le key listener
		public void keyTyped(KeyEvent event) {
			if (event.getKeyChar() == 'q') {
				directionJ1 = GAUCHE;
			}
			if (event.getKeyChar() == 's') {
				directionJ1 = BAS;
			}
			if (event.getKeyChar() == 'd') {
				directionJ1 = DROITE;
			}
			if (event.getKeyChar() == 'z') {
				directionJ1 = HAUT;
			}
		}
	}

	public class Mouvement2 extends Thread implements KeyListener {
		public void run() {


			// activer Keylistener
			addKeyListener(this);
			// boucle infini
			while (true) {
				// arret en cs d'erreur
				try {
					// refresh
					repaint();

					// augmentation de la vitesse
					if (vitesseJ2 <= 5)
						vitesseJ2 += .2;

					// deplacement en fonction de la direction
					if (directionJ2 == HAUT) {
						j2.y -= (int) vitesseJ2;
					}
					if (directionJ2 == BAS) {
						j2.y += (int) vitesseJ2;
					}
					if (directionJ2 == GAUCHE) {
						j2.x -= (int) vitesseJ2;
					}
					if (directionJ2 == DROITE) {
						j2.x += (int) vitesseJ2;
					}
		
					Thread.sleep(75);
				} catch (Exception e) {
					break;
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() == 'j') {
				directionJ2 = GAUCHE;
			}
			if (e.getKeyChar() == 'k') {
				directionJ2 = BAS;
			}
			if (e.getKeyChar() == 'l') {
				directionJ2 = DROITE;
			}
			if (e.getKeyChar() == 'i') {
				directionJ2 = HAUT;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}
}