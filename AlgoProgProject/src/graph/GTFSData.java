package graph;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import data.*;

public class GTFSData {
	private TreeMap<Long, Agency> agencies;
	private TreeMap<Long, Route> routes;
	private TreeMap<Long, Trip> trips;
	private TreeMap<Long, Stop> stops;
	private TreeMap<Long, List<StopTime>> stoptimesByTrip;
	private TreeMap<Long, List<StopTime>> stoptimesByStop;
	private TreeMap<Long, List<Transfer>> transfers;
	
	public static void main(String[] args) {
		GTFSData gtfsData = new GTFSData("./ressources");
		String stationName="Nation", departure="14:37:00";
		
		// Ligne 10 Aller - départ 17:06:00
		gtfsData.printTransfersFromTrip(10022803710917606L);
		// Ligne 10 Retour - départ 17:05:00
		gtfsData.printTransfersFromTrip(10022803710296022L);
		
		// OrlyVal Aller
		//gtfsData.printTransfersFromTrip(10500872820081804L);
		

		//gtfsData.printLineFromStationAndTime(stationName, departure);
		
	}
	
	public GTFSData(String path) {
		File rep = new File(path);
		agencies = new TreeMap<Long, Agency>();
		routes = new TreeMap<Long, Route>();
		trips = new TreeMap<Long, Trip>();
		stops = new TreeMap<Long, Stop>();
		stoptimesByTrip = new TreeMap<Long, List<StopTime>>();
		stoptimesByStop = new TreeMap<Long, List<StopTime>>();
		transfers = new TreeMap<Long, List<Transfer>>();
		parcourirRepertoire(rep);
	}
	
	public void printTransfersFromTrip(Long tripId) {
		String print = "";
		int cpt=0, cptTrans, sautLigne=1;
		boolean transferMetro = false;
		Route routeFrom = null, routeTo = null;
		for(StopTime stoptime : getStoptimesByTrip().get(tripId)) {
			transferMetro = false;
			cptTrans=0;
			System.out.println(stoptime.getStop_sequence() + " " + getStops().get(stoptime.getStop_id()).getStop_name()
					+ " - StopId " + getStops().get(stoptime.getStop_id()).getStop_id()
					// + " - Departure " + stoptime.getDeparture_time()
					// + " - Sequence "+ stoptime.getStop_sequence() + "] "
					// + " Trip headsign " + getTrips().get(tripId).getTrip_headsign()
					);
			if (getTransfers().get(stoptime.getStop_id())!= null) {
				for (Transfer trans : getTransfers().get(stoptime.getStop_id())) {
					if (getStops().get(trans.getFromId())!=null 
							&& getStops().get(trans.getToId())!=null) {
						routeFrom = getStoptimesByStop().get(trans.getFromId())
								.get(0).getTrip().getRoute();
						routeTo = getStoptimesByStop().get(trans.getToId())
								.get(0).getTrip().getRoute();
						print = "\t{ Transfer from [line ";
						print+= routeFrom.getRoute_short_name();
						print += " " + routeFrom.getRoute_long_name() + "]";
						print+= " to [line ";
						print+= routeTo.getRoute_short_name();
						print += " " + routeTo.getRoute_long_name() + "]";
						print+= " }, ";
						System.out.print(print);
						cptTrans++;
						if (cptTrans%sautLigne==0) {
							System.out.println();
						}
						transferMetro = true;
					}
				}
				if (cptTrans%sautLigne!=0) {
					System.out.println();
				}
			}
			if (!transferMetro) {
				System.out.println("\tNo transfer to an other metro line");
			}
			cpt++;
		}
		System.out.println("\nNombre d'arrets (stop) du trajet " +cpt + "\n");
	}
	
	public void printLineFromStationAndTime(String stationName, String time) {
		String print = "";
		int cpt=0, cptTrans, sautLigne=1;
		boolean transferMetro = false;
		Long stationId = null;
		System.out.println("Recherche station " + stationName + " départ à " + time);
		for(Long stopIt = getStops().firstKey();stopIt!=null;stopIt = getStops().higherKey(stopIt)) {
			if (getStops().get(stopIt).getStop_name().equals(stationName)) {
				stationId = stopIt;
				System.out.println("station ID : " + stationId);
				break;
			}
		}
		Route routeFrom = null, routeTo = null;
		for(StopTime stopTime : getStoptimesByStop().get(stationId)) {
			System.out.println("Arrivé à "+stopTime.getArrival_time());
		}
	}
	
	public void parcourirRepertoire ( File repertoire )
	{
	    if (repertoire.exists())
	    {
	    	if (repertoire.isDirectory())
		    {
		        File[] fichiers = repertoire.listFiles();
		        if (fichiers != null)
		        {
		        	if (fichiers[0].getName().equals("agency.txt")) {
			            try {
			            	System.out.println(repertoire.getPath());
							readFiles(repertoire);
						} catch (IOException e) {
							System.out.println("Erreur de lecture du fichier : " 
									+ repertoire.getPath());
							e.printStackTrace();
						}
		        		
		        	}
		        	else {
			            for (File fichier : fichiers) { 
			            	parcourirRepertoire(fichier); 
			            }
		        	}
		        }
		        else 
		        {
		            System.err.println(repertoire + " : Erreur de lecture."); 
		        }
		    }
	    	else 
	    	{
	    		System.out.println ( repertoire.getAbsolutePath() + " " + repertoire.getName());
	    	}
	    }
	}
	
	public void readFiles(File file) throws IOException {
		File[] files = file.listFiles();
		int cpt=0;
		if (files.length < 8) {
			return;
		}
		while (cpt<files.length && cpt<8) {
			for (int i = 0; i <files.length; i++) {
				if (cpt==0 && files[i].getName().equals("agency.txt")) {
					readAgency(files[i]);
					cpt++;
				}
				else if (cpt==1 && files[i].getName().equals("calendar_dates.txt"))
				{
					readCalendarDate(files[i]);
					cpt++;
				}
				else if (cpt==2 && files[i].getName().equals("calendar.txt"))
				{
					readCalendar(files[i]);
					cpt++;
				}
				else if (cpt==3 && files[i].getName().equals("routes.txt"))
				{
					readRoutes(files[i]);
					cpt++;
				}
				else if (cpt==4 && files[i].getName().equals("trips.txt")) {
					readTrips(files[i]);
					cpt++;
				}
				else if (cpt==5 && files[i].getName().equals("stops.txt")) {
					readStops(files[i]);
					cpt++;
				}
				else if (cpt==6 && files[i].getName().equals("stop_times.txt")) {
					readStopTimes(files[i]);
					cpt++;
				}
				else if (cpt==7 && files[i].getName().equals("transfers.txt")) {
					readTranfers(files[i]);
					cpt++;
				}
			}
		}
		//System.out.println("read Files done");
	}

	private void readTrips(File file) throws IOException {
		Trip trip;
		BufferedReader br = new BufferedReader(new FileReader(file));  
		String line = null;  
		String[] lineData;
		Long tripId, routeId, tripHS,
			tripSN, direct, serviceId;
		br.readLine();
		while ((line = br.readLine()) != null)  
		{
		   lineData = line.split(",",-1);
		   if (lineData.length<6) {
				br.close();
			   throw new IOException("Erreur format at line : " + line);
		   }
		   //System.out.println(line);
		   routeId = Long.valueOf(lineData[0]);
		   serviceId = Long.valueOf(lineData[1]);
		   tripId = Long.valueOf(lineData[2]);
		   tripHS = Long.valueOf(lineData[3]);
		   tripSN = Long.valueOf(lineData[4]);
		   direct = Long.valueOf(lineData[5]);
		   trip = new Trip(tripId, tripHS, tripSN, direct, serviceId, routes.get(routeId));
		   trips.put(tripId, trip);
		}
		br.close();
	}

	private void readTranfers(File file) throws IOException {
		Transfer transferFrom, transferTo;
		BufferedReader br = new BufferedReader(new FileReader(file));  
		String line = null;  
		String[] lineData;
		Long fromId, toId;
		br.readLine();
		while ((line = br.readLine()) != null)  
		{
		   lineData = line.split(",",-1);
		   if (lineData.length<4) {
				br.close();
			   throw new IOException("Erreur format at line : " + line);
		   }
		   fromId = Long.valueOf(lineData[0]);
		   toId = Long.valueOf(lineData[1]);
		   //System.out.println(fromKey + " " + toKey);
		   //System.out.println(line);
		   if (!transfers.containsKey(fromId)) {
			   transfers.put(fromId, new ArrayList<Transfer>());
		   }
		   if (!transfers.containsKey(toId)) {
			   transfers.put(toId, new ArrayList<Transfer>());
		   }
		   //System.out.println(from);
		   transferFrom = new Transfer(fromId, toId, Integer.valueOf(lineData[2]), Long.valueOf(lineData[3]));
		   if (!transfers.get(fromId).contains(transferFrom)) {
			   transfers.get(fromId).add(transferFrom);
		   }
		   transferTo = new Transfer(toId, fromId, Integer.valueOf(lineData[2]), Long.valueOf(lineData[3]));
		   if (!transfers.get(toId).contains(transferTo)) {
				transfers.get(toId).add(transferTo);
		   }
		}
		br.close();
	}

	private void readStops(File file) throws IOException {
		Stop stop;
		BufferedReader br = new BufferedReader(new FileReader(file));  
		String line = null;  
		String[] lineData;
		br.readLine();
		while ((line = br.readLine()) != null)  
		{  
		   lineData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)",-1);
		   if (lineData.length<7) {
				br.close();
			   throw new IOException("Erreur format at line : " + line);
		   }
		   //System.out.println(line);
		   stop = new Stop(Long.valueOf(lineData[0])
				   , lineData[2], lineData[3], Double.valueOf(lineData[4])
				   , Double.valueOf(lineData[5]), Integer.valueOf(lineData[6]));
		   stops.put(stop.getStop_id(),stop);
		}
		br.close();
	}

	private void readStopTimes(File file) throws IOException {
		StopTime stoptime;
		BufferedReader br = new BufferedReader(new FileReader(file));  
		String line = null;  
		String[] lineData;
		Long tripId, stopId, seq;
		br.readLine();
		while ((line = br.readLine()) != null)  
		{  
		   lineData = line.split(",",-1);
		   if (lineData.length<7) {
				br.close();
			   throw new IOException("Erreur format at line : " + line);
		   }
		   //System.out.println(line);
		   tripId = Long.valueOf(lineData[0]);
		   stopId = Long.valueOf(lineData[3]);
		   seq = Long.valueOf(lineData[4]);
		   stoptime = new StopTime(trips.get(tripId), lineData[1], lineData[2], seq
				   , stopId);
		   // Add to stopTimesByTrip
		   if (!stoptimesByTrip.containsKey(tripId)) {
			   stoptimesByTrip.put(tripId, new ArrayList<StopTime>());
		   }
		   stoptimesByTrip.get(tripId).add(stoptime);
		   // Add to stopTimesByStop
		   if (!stoptimesByStop.containsKey(stopId)) {
			   stoptimesByStop.put(stopId, new ArrayList<StopTime>());
		   }
		   stoptimesByStop.get(stopId).add(stoptime);
		}
		br.close();
	}

	private void readRoutes(File file) throws IOException {
		Route route;
		BufferedReader br = new BufferedReader(new FileReader(file));  
		String line = null;  
		String[] lineData;
		Long routeId, agencyId;
		int type;
		br.readLine();
		while ((line = br.readLine()) != null)  
		{  
		   lineData = line.split(",",-1);
		   if (lineData.length<9) {
				br.close();
			   throw new IOException("Erreur format at line : " + line);
		   }
		   //System.out.println(line);
		   routeId = Long.valueOf(lineData[0]);
		   agencyId = Long.valueOf(lineData[1]);
		   type = Integer.valueOf(lineData[5]);
		   route = new Route(routeId, lineData[2], lineData[3], type, agencies.get(agencyId));
		   routes.put(routeId, route);
		}
		br.close();
	}

	private void readCalendar(File file) {
		
		
	}

	private void readCalendarDate(File file) {
		
		
	}

	private void readAgency(File file) throws IOException {
		Agency agency;
		BufferedReader br = new BufferedReader(new FileReader(file));  
		String line = null;  
		String[] lineData;
		Long agencyId;
		br.readLine();
		while ((line = br.readLine()) != null)  
		{  
		   lineData = line.split(",",-1);
		   if (lineData.length<4) {
				br.close();
			   throw new IOException("Erreur format at line : " + line);
		   }
		   //System.out.println(line);
		   agencyId = Long.valueOf(lineData[0]);
		   agency = new Agency(agencyId, lineData[1], lineData[2], lineData[3]);
		   agencies.put(agencyId, agency);
		}
		br.close();
	}

	public TreeMap<Long, Agency> getAgencies() {
		return agencies;
	}

	public TreeMap<Long, Route> getRoutes() {
		return routes;
	}

	public TreeMap<Long, Trip> getTrips() {
		return trips;
	}

	public TreeMap<Long, Stop> getStops() {
		return stops;
	}

	public TreeMap<Long, List<StopTime>> getStoptimesByTrip() {
		return stoptimesByTrip;
	}
	
	public TreeMap<Long, List<StopTime>> getStoptimesByStop() {
		return stoptimesByStop;
	}

	public TreeMap<Long, List<Transfer>> getTransfers() {
		return transfers;
	}
}
