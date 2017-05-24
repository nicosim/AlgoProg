import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.TransferHandler.TransferSupport;

import data.*;

public class GTFSData {
	private TreeMap<Long, Agency> agencies;
	private TreeMap<Long, Route> routes;
	private TreeMap<Long, Trip> trips;
	private TreeMap<Long, Stop> stops;
	private TreeMap<Long, List<StopTime>> stoptimes;
	private TreeMap<Long, List<Transfer>> transfers;
	
	public static void main(String[] args) {
		GTFSData gtfsData = new GTFSData("./ressources");
	}
	
	public GTFSData(String path) {
		File rep = new File(path);
		int cpt=0,cptTrip=0;
		agencies = new TreeMap<Long, Agency>();
		routes = new TreeMap<Long, Route>();
		trips = new TreeMap<Long, Trip>();
		stops = new TreeMap<Long, Stop>();
		stoptimes = new TreeMap<Long, List<StopTime>>();
		transfers = new TreeMap<Long, List<Transfer>>();
		parcourirRepertoire(rep);
		Long tripId = 10022802431078872L;
		for(StopTime stoptime : stoptimes.get(tripId)) {
			System.out.print("[ Stop" + stops.get(stoptime.getStop_id()).getStop_name()
					+ " - Departure " + stoptime.getDeparture_time()
					+ " - Sequence "+ stoptime.getStop_sequence() + "] ");
			cpt++;
			if (cpt == 1) {
				cpt = 0;
				System.out.println();
			}
		}
		
		
		/*
		for (Long tripId = trips.firstKey(); tripId!=null;tripId=trips.higherKey(tripId)) {
			System.out.println("Stop times of tripId "+ tripId + " : ");
			for(StopTime stoptime : stoptimes.get(tripId)) {
				System.out.print("[ Stop" + stoptime.getStop_id()  + " - Sequence "+ stoptime.getStop_sequence() + "] ");
				cpt++;
				if (cpt == 20) {
					cpt = 0;
					System.out.println();
				}
			}
			System.out.println();
			cptTrip++;
			if (cptTrip == 50) {
				cptTrip = 0;
				break;
			}
		}
		*/
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
			            	//System.out.println(repertoire.getPath());
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
		if (files.length != 8) {
			return;
		}
		if (files[0].getName().equals("agency.txt"))
			readAgency(files[0]);
		if (files[1].getName().equals("calendar_dates.txt"))
			readCalendarDate(files[1]);
		if (files[2].getName().equals("calendar.txt"))
			readCalendar(files[2]);
		if (files[3].getName().equals("routes.txt"))
			readRoutes(files[3]);
		if (files[7].getName().equals("trips.txt"))
			readTrips(files[7]);
		if (files[5].getName().equals("stops.txt"))
			readStops(files[5]);
		if (files[4].getName().equals("stop_times.txt"))
			readStopTimes(files[4]);
		if (files[6].getName().equals("transfers.txt"))
			readTranfers(files[6]);
		/*
			System.out.println("Agencies " + agencies);
			System.out.println("Routes " + routes);
			System.out.println("Stops " + stops);
			//System.out.println("Stop times " + stoptimes);
			System.out.println("Transfers " + transfers);
			System.out.println("Trips " + trips);
		*/
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
		Transfer transfer;
		BufferedReader br = new BufferedReader(new FileReader(file));  
		String line = null;  
		String[] lineData;
		Long fromId, toId;
		br.readLine();
		while ((line = br.readLine()) != null)  
		{
		   lineData = line.split(",",-1);
		   if (lineData.length<4) {
			   throw new IOException("Erreur format at line : " + line);
		   }
		   fromId = Long.valueOf(lineData[0]);
		   toId = Long.valueOf(lineData[1]);
		   //System.out.println(fromKey + " " + toKey);
		   //System.out.println(line);
		   if (!transfers.containsKey(fromId)) {
			   transfers.put(fromId, new ArrayList<Transfer>());
		   }
		   //System.out.println(from);
		   transfer = new Transfer(fromId, toId, Integer.valueOf(lineData[2]), Long.valueOf(lineData[3]));
		   transfers.get(fromId).add(transfer);
		   
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
			   throw new IOException("Erreur format at line : " + line);
		   }
		   //System.out.println(line);
		   tripId = Long.valueOf(lineData[0]);
		   stopId = Long.valueOf(lineData[3]);
		   seq = Long.valueOf(lineData[4]);
		   stoptime = new StopTime(trips.get(tripId), lineData[1], lineData[2], seq
				   , stopId);
		   if (!stoptimes.containsKey(tripId)) {
			   stoptimes.put(tripId, new ArrayList<StopTime>());
		   }
		   stoptimes.get(tripId).add(stoptime);
		}
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
			   throw new IOException("Erreur format at line : " + line);
		   }
		   //System.out.println(line);
		   agencyId = Long.valueOf(lineData[0]);
		   agency = new Agency(agencyId, lineData[1], lineData[2], lineData[3]);
		   agencies.put(agencyId, agency);
		}
		br.close();
	}
}
